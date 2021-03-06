package com.yq.controller;

import com.yq.domain.Log;
import com.yq.domain.Movie;
import com.yq.service.LogService;
import com.yq.service.MovieService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.xml.ws.Response;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Created by nasir on 14/11/17.
 */
@RestController
public class MovieResource {

    private MovieService movieService;

    @Autowired
    public MovieResource(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/movie/add")
    @ApiOperation(value = "add", notes="private")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "newMovie", value = "newMovie", required = true, dataType = "newMovie", paramType = "body")
    })
    public ResponseEntity<Movie> addMovie(@RequestBody  Movie newMovie) {
        Movie savedMovie = movieService.addMovie(newMovie);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
                "/{id}").buildAndExpand(savedMovie.getId()).toUri();
        return ResponseEntity.ok(savedMovie).created(location).build();
    }

    @ApiOperation(value = "add", notes="private")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "long", paramType = "path")
    })
    @DeleteMapping("/movie/{id}/delete")
    public ResponseEntity<String> deleteMovie(@PathVariable("id") Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok("Deleted");
    }

    @ApiOperation(value = "add", notes="private")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "name", required = true, dataType = "string", paramType = "path")
    })
    @GetMapping("/movie/get-by-name/{name}")
    public ResponseEntity<List<Movie>> findMovieByName(@PathVariable("name") String name) {
        List<Movie> fetchedMovie = movieService.getByName(name);
        return ResponseEntity.ok(fetchedMovie);
    }
}