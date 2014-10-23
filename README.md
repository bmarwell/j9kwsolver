j9kwsolver
==========

## Build Status
[![Build Status](https://travis-ci.org/bmhm/j9kwsolver.svg?branch=next)](https://travis-ci.org/bmhm/j9kwsolver)

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
None at the moment, really. You can compile this in eclipse
and execute `java de.bmarwell.j9kwsolver.test.J9kwSolver.class -Ddebug=true`
to see if it works for you. It's command line only at the moment.

## Sources
See [9kw API](http://www.9kw.eu/api.html#apisolve-tab "9kw API solve") 
for more information.

