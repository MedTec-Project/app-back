
package br.medtec.features.history;

import br.medtec.utils.ResponseUtils;
import br.medtec.utils.UtilDate;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/history")
public class HistoryResource {

    final HistoryRepository historyRepository;

    @Inject
    public HistoryResource(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHistory() {
        try {
            List<History> history = historyRepository.findAll();
            history.forEach(h -> {
                h.setDate(UtilDate.formatTimestamp(h.getCreationDate()));
                h.setTypeString(h.getType().getDescription());
            });

            return ResponseUtils.ok(history);
        } catch (Exception e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }
}
