package br.com.emmanuelneri.service;

import br.com.emmanuelneri.model.BillFile;
import br.com.emmanuelneri.repository.BillFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillFileService {

    @Autowired
    private BillFileRepository billFileRepository;

    public void save(String content) {
        billFileRepository.save(new BillFile(content));
    }

    public List<BillFile> findAll() {
        return billFileRepository.findAll();
    }

    public void delete(String id) {
        billFileRepository.delete(id);
    }

}
