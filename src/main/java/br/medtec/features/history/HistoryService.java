package br.medtec.features.history;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HistoryService {

    final HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public void save(String title, HistoryType type) {
        save(title, null, type);
    }

    public void save(String title, String description, HistoryType type) {
        History history = new History();
        history.setTitle(title);
        history.setDescription(description);
        history.setType(type);
        historyRepository.save(history);
    }


}
