# Falkor

A web service for turning HTML pages into traversable JSON documents

Very early stage development. If you have any feature requests just create an issue on the project

## Getting started

Running the server locally

```
lein uberjar
docker build -t falkor .
docker run -t falkor

# Visit http://localhost:5000
```

## Comming soon

+ Better error handling
+ CORS
+ Query filtering (return only certain attributes)
+ Fetching multiple elements in a single request ( e.g [h1 > a, .subtitle] )

## Usage

Get all the title links from the Reddit.com home page


https://falkor-api.herokuapp.com/api/query?url=http://reddit.com&q=a.title

Grab all the news stories from Digg.com

https://falkor-api.herokuapp.com/api/query?url=http://digg.com&q=.story-title%20a

Extract all the images from Digg.com

https://falkor-api.herokuapp.com/api/query?url=http://digg.com&q=img[src]

## TODO

Filters to remove some of the attribute cruft

For example if we just want to extract the text for an element and ignore the other attributes

```
&filter=[text]
```

## License

Copyright © 2015 Forward Digital Limited

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
