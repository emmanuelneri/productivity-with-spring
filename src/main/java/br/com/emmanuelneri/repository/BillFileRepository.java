package br.com.emmanuelneri.repository;

import br.com.emmanuelneri.model.BillFile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BillFileRepository extends MongoRepository<BillFile, String> {
}
