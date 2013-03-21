package poc.nosql.publishedodds.repositories;

import org.springframework.data.neo4j.repository.GraphRepository;
import poc.nosql.publishedodds.entities.Book;

public interface BooksRepository extends GraphRepository<Book> {
}
