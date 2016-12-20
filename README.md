j9kwsolver
==========

An Open Source solver tool for 9kw.eu captcha service.

## Build Status
[![Build Status](https://travis-ci.org/bmhm/j9kwsolver.svg?branch=next)](https://travis-ci.org/bmhm/j9kwsolver)

**Broken**, because the debug mode still needs a valid api key.

## Goal
Providing a stable, open source, Java API for 9kw captcha service.
A stable GUI might be added some time later (v2.0 or so).

## Features
* Get Captchas without concerning about technical issues.
* Easy to use.
* Threading provided (for http requests).
* Source code is maintainable and uses well-tested third-party libraries.

## Usage

### APIKEY
Create a configfile (like so):
`touch $USER/.config/j9kwsolver.conf`

Add this contents:
```
apikey = <APIKEY>
debug = 0
```

### Startup
Start the gui: `java -cp (…*.jar) -Dconfig.file=$USER/.config/j9kwsolver.conf `

## Sources
See [9kw API](http://www.9kw.eu/api.html#apisolve-tab "9kw API solve")
for more information.

