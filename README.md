# Falkor

A web service for turning HTML pages into traversable JSON documents

Very early stage development. Everything will break.

![](http://elitistjerksports.com/wp-content/uploads/2010/07/falkor-3.jpg)

## Usage

Extract all the homepage stories from BBC news

```
CURL http://localhost:3000/api/query?url=http://bbc.co.uk/news&q=.title-link%20h3%20span
```

Sample response

```json
[
{
"attributes": {
"class": "title-link__title-text"
},
"tag": "span",
"text": "Missing Ethan reunited with father"
},
{
"attributes": {
"class": "title-link__title-text"
},
"tag": "span",
"text": "Clinton rally woos working Americans"
},
{
"attributes": {
"class": "title-link__title-text"
},
"tag": "span",
"text": "Foos cancel two shows after Grohl fall"
} ...
```

Extract all images from github.com

```
CURL http://localhost:3001/api/query?url=http://github.com&q=img
```

## License

Copyright © 2015 Forward Digital Limited

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
