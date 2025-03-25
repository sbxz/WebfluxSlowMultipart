package com.example.reactivemultipart;

import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.FilePartEvent;
import org.springframework.http.codec.multipart.FormPartEvent;
import org.springframework.http.codec.multipart.PartEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FileController {

    /**
     * With the FilePart endpoint for a 200mb file, it takes approximately 4.2s for the request
     */
    @PostMapping(path = "/file-part", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<Void> uploadFilePart(@RequestPart("file") FilePart file) {
        log.info("Received file part request {}", file.filename());
        return Mono.empty();
    }

    /**
     * With the part-event endpoint for a 200mb file, it takes approximately 4.5s for the request
     */
    @PostMapping(path = "/part-event", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<Void> uploadPartEvent(@RequestBody Flux<PartEvent> allPartsEvents) {
        return allPartsEvents.windowUntil(PartEvent::isLast)
            .concatMap(p -> p.switchOnFirst((signal, partEvents) -> {
                if (signal.hasValue()) {
                    PartEvent event = signal.get();
                    if (event instanceof FormPartEvent formEvent) {
                        return Mono.error(new RuntimeException("Unexpected event: " + event));
                    } else if (event instanceof FilePartEvent fileEvent) {
                        log.info("Received part event request {}", fileEvent.filename());
                        return Mono.empty();
                    } else {
                        return Mono.error(new RuntimeException("Unexpected event: " + event));
                    }
                } else {
                    return partEvents;
                }
            })).then();
    }
}
