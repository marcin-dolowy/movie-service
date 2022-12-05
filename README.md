# Movie Service

## Description
An application for searching movies, that uses OMDb API. The application includes a front-end designed with the Vaadin framework. 
It is connected with H2 database. 

## About the API
The OMDb API is a RESTful web service to obtain movie information, all content and images on the site are contributed and maintained by our users.

## Features
- Movie search.
- Add movie to favorites.
- Save favourite movies to database.

### This API provides HTTP endpoint's and tool for the following:

- Find all favourite movies: `GET/api/movies/all`
- Find a movie by id: `GET/api/movies/?id={id}`
- Delete a movie: `DELETE/api/movies/?id={}`

### Details

`POST/api/movies`

This end-point is called to add favourite movie.

**Body:**
```json
{
  "imdbID": "tt123456",
  "title": "abc",
  "plot": "abc",
  "genre": "abc",
  "director": "abc",
  "poster": "url"
}
```
\
`PATCH/api/movies/update/{id}`

This end-point is called to update a movie.

**Body:**
```json
{
  "plot": "new plot",
  "director": "new director"
}
```

**Result:**
```json
{
  "imdbID": "tt123456",
  "title": "abc",
  "plot": "new plot",
  "genre": "abc",
  "director": "new director",
  "poster": "url"
}
```

### Technologies used

This project was developed with:

- **Java 17**
- **Spring Boot**
- **Spring Data**
- **H2 Database**
- **Hibernate**
- **Gradle**
- **Vaadin**

## License
All content licensed under CC BY-NC 4.0.
