# Falkor

A web service for turning HTML pages into traversable JSON documents

Very early stage development. Everything will break.

## Usage

Get all the title links from the Reddit.com home page

```
https://falkor-api.herokuapp.com/api/query?url=http://reddit.com&q=a.title
```
 
 Grab all the news stories from Digg.com

```
https://falkor-api.herokuapp.com/api/query?url=http://digg.com&q=.story-title%20a
```

Extract all the images from Digg.com

https://falkor-api.herokuapp.com/api/query?url=http://digg.com&q=img[src]

## License

Copyright © 2015 Forward Digital Limited

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
