# Linkoln2 Service

This project exposes the [Linkoln2 project](https://gitlab.com/IGSG/LINKOLN/linkoln) 
as a ReST service or JAR callable program.

More specifically it is a SpringBoot wrapper that embed the Linkoln2 parser version 2.2.0.

## ReST service

The service's endpoints are the following:

* Swagger opendocs: http://localhost:5021/swagger-ui.html
* Parse a text file's content (also HTML):
  * Response contains the whole parsed file content: http://localhost:5021/v2/linkoln/parse/{interno}/file
  * Response contains the list of the recognized references: http://localhost:5021/v2/linkoln/parse/{interno}/list

## Cmdline callable JAR

Using the pom.cmd.xml it is possible to build a Java JAR for a batch execution of the parser. 

In this first version you need to pass the whole text file's content as the unique argument. 
In standard output there is the parsed text.   

## License

This project is licensed under the [GPL 3](/LICENSE). Copyright (c) 2020-2021 Senato della Repubblica.

The Linkoln2 library is licensed under the [GPL 3](/LICENSE). Copyright (c) Institute of Legal Information and Judicial Systems IGSG-CNR (formerly ITTIG-CNR)
