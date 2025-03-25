# WebfluxSlowMultipart

## ReactiveMultipart

### Endpoint file part

With the FilePart endpoint for a 200mb file, it takes approximately 4.2s for the request.

```
curl --location 'http://localhost:8080/file-part' \
--form 'file=@"/XXX/200mb.pdf"'
```

### Endpoint part event

With the part-event endpoint for a 200mb file, it takes approximately 4.5s for the request.

```
curl --location 'http://localhost:8080/part-event' \
--form 'file=@"/XXX/200mb.pdf"'
```

## MvcMultipart

### Endpoint multipart file

With the MultipartFile endpoint for a 200mb file, it takes approximately 700ms for the request.

```
curl --location 'http://localhost:8080/multipart-file' \
--form 'file=@"/XXX/200mb.pdf"'
```


