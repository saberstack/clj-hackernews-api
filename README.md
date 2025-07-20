# HackerNews API Client

A Clojure client library for the [Official HackerNews API](https://github.com/HackerNews/API) built using `babashka.http-client`.

## Overview

This library provides a simple interface to interact with the HackerNews Firebase API, allowing you to retrieve stories, comments, user information, and other data from HackerNews.

## Installation

Add the following dependency to your `deps.edn`:

```clojure
org.babashka/http-client {:mvn/version "0.4.22"}
```


## API Reference

All functions return HTTP response maps from `babashka.http-client/get`.

### Items

#### `item!`
Retrieves any item (story, comment, poll, etc.) by its ID.

```clojure
(item! 42)
```


**Parameters:**
- `id` - The item's unique ID

### Users

#### `user!`
Retrieves user information by username.

```clojure
(user! "raspasov")
```


**Parameters:**
- `username` - The user's username (case-sensitive)

**Note:** Usernames are case-sensitive.

### Stories

#### `new-stories!`
Returns up to 500 new stories.

```clojure
(new-stories!)
```


#### `top-stories!`
Returns up to 500 top stories.

```clojure
(top-stories!)
```


#### `best-stories!`
Returns up to 500 best stories.

```clojure
(best-stories!)
```


#### `ask-stories!`
Returns up to 200 Ask HN stories.

```clojure
(ask-stories!)
```


#### `show-stories!`
Returns up to 200 Show HN stories.

```clojure
(show-stories!)
```


#### `job-stories!`
Returns up to 200 job stories.

```clojure
(job-stories!)
```


### Metadata

#### `max-item!`
Returns the current largest item ID.

```clojure
(max-item!)
```


#### `updates!`
Returns recent item and profile changes.

```clojure
(updates!)
```


## Usage Examples

```clojure
(ns my.app
  (:require [org.saberstack.hn.api-v0 :as hn]))

;; Get a specific item
(hn/item! 42)

;; Get user information
(hn/user! "pg")

;; Get top stories
(hn/top-stories!)

;; Get the highest item ID
(hn/max-item!)
```


## Response Format

All functions return HTTP response maps with the following structure:

```clojure
{:status 200
 :headers {...}
 :body "..."}  ; body is a string containing JSON
```


The actual HackerNews data will be in the `:body` key of the response.

## API Endpoints

This library wraps the following HackerNews API endpoints:

- `https://hacker-news.firebaseio.com/v0/item/{id}.json`
- `https://hacker-news.firebaseio.com/v0/user/{username}.json`
- `https://hacker-news.firebaseio.com/v0/maxitem.json`
- `https://hacker-news.firebaseio.com/v0/updates.json`
- `https://hacker-news.firebaseio.com/v0/topstories.json`
- `https://hacker-news.firebaseio.com/v0/newstories.json`
- `https://hacker-news.firebaseio.com/v0/beststories.json`
- `https://hacker-news.firebaseio.com/v0/askstories.json`
- `https://hacker-news.firebaseio.com/v0/showstories.json`
- `https://hacker-news.firebaseio.com/v0/jobstories.json`

For detailed information about the API and response formats, refer to the [Official HackerNews API documentation](https://github.com/HackerNews/API).
