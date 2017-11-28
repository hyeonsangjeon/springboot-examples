# rest-jpa-h2-example

This example shows how you can make MVC REST API Spring Boot, having:

- Spring container creating
- a standard MVC Springboot web for the REST API
- Spring backend services (with declarative transaction management and JPA repositories)

To try the example, execute this command in a terminal:

[source,shell]
----
mvn clean spring-boot:run
----

Then add two or more books to the database at ternimal with containers.:

[source,shell]
----
curl -H "Accept: application/json" -H "Content-Type: application/json" -X POST -d '{"name":"Disparity based space variant image deblurring, Signal Processing: Image Communication","author":"ChangsooJe,HyeonSangJeon,ChangHwanSon,HyungMinPark", "pages":792'} http://localhost:8989/book/add
----
curl -H "Accept: application/json" -H "Content-Type: application/json" -X POST -d '{"name": "The Practice of System and Network Administration", "author":"Thomas A. Limoncelli, Christina J. Hn, Strata R. Chalup"}' http://localhost:8989/book/add
----


You should now be able to see your all book saved:

[source,shell]
----
curl -H "Accept: application/json" -H "Content-Type: application/json" -X GET  http://localhost:8989/book
----

Notice that the `id` field is now set which means that the book as indeed been persisted in the database.

And you can be able to see id based specify 1 book:

[source,shell]
----
curl -H "Accept: application/json" -H "Content-Type: application/json" -X GET  http://localhost:8989/book/bookId?bookId=1
----

Let 's update the author of your book.

[source,shell]
----
curl -H "Accept: application/json" -H "Content-Type: application/json" -X PUT -d '{"name":"father", "id":"1"}' http://localhost:8989/book/bookId
----

Let's delete the saved book.

[source,shell]
----
curl -H "Accept: application/json" -H "Content-Type: application/json" -X DELETE  -d '{"id":"1"}' http://localhost:8989/book/bookId
----
