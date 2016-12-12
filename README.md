j9kwsolver
==========

## Build Status
[![Build Status](https://travis-ci.org/bmhm/j9kwsolver.svg?branch=next)](https://travis-ci.org/bmhm/j9kwsolver)
**Broken**, because the json interface of 9kw is just awful at the moment. Return type is often text/html, png is being sent as binary data.

An Open Source solver tool for 9kw.eu captcha service.

## Goal
Providing a stable, open source, Java API for 9kw captcha service.
A GUI might be added some time later (v2.0 or so).

## Features
* Get Captchas without concerning about technical issues.
* Easy to use.
* Threading provided (for http requests).
* Source code is maintainable and uses well-tested third-party libraries.

## Usage

### APIKEY
To use your `apikey`, use this command:
`$ echo "apikey" > ~/.config/j9kwsolver/apikey`

### Startup
GUI was removed for version 1.0.0.
Transition to multi module project immanent.

## Sources
See [9kw API](http://www.9kw.eu/api.html#apisolve-tab "9kw API solve")
for more information.

