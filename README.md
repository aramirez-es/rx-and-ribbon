# Testing rxjava with Ribbon
The main purpose of this repo is to test together rxjava (mainly Zip operator) and Ribbon+Hystrix.
Zip allows to execute n Observables and merge couple of events using a merging function. With Ribbon, we can make parallel requests (to different micr-services for example) and aggregate that information. 
This tests starts because of an issued found out with *Observable.zip* and *Ribbon Commands* in a private project, causing memory leaks.

## Dependencies
- http://docs.locust.io/en/latest/index.html
- https://github.com/joke2k/faker

## Scenarios
There are different scenarios here (all of them sharing a common service using Zip operator):
- Repositories that emit a single value using *Observable.just* to simulate sync operations.
- Repositories that use *Observable.defer* and *Schedulers.io* and *Schedulers.computation* in Observable creation and subscription, to simulate async operations.
- Repositories as the previous ones but adding random delay to test services with *latency*.
- (WIP) Repositories making requests to external services using *Ribbon Commands*.


## Execute
To chose what scenario to run, you can give the run command the *env* property:

```bash
$ ./gradlew clean run -Denv=sync
```

Once the service is running, we use locust to take it under load and try to reproduce production situations. 

```bash
$ locust -f agent.py
```

## Display Information
When the project runs, it displays the following information:

```bash
#======================#
# Free Memory:  226 MB #
# Total Memory: 245 MB #
# Max Memory:   3 GB   #
#======================#
```