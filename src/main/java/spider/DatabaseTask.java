package spider;

import database.Database;
import database.models.Word;
import logger.LoggerUtils;
import utils.Link;
import utils.LinkFactory;

import java.util.Collection;
import java.util.stream.Collectors;

public class DatabaseTask {
    private final Database database;
    private final Link domain;
    private final Collection<String> words;

    DatabaseTask(Database database, Link domain, Collection<String> words) {
        this.database = database;
        this.domain = domain;
        this.words = words;
    }

    boolean run() {
        try {
            LoggerUtils.debugLog.info("Database task start");
            if (words.isEmpty()) {
                LoggerUtils.debugLog.error("DatabaseTask - An empty list of words came to the database " + domain);
                return false;
            } else {
                return database.putWords(
                        words.stream()
                                .map(word -> Word.newInstance(LinkFactory.getDomainId(domain), word))
                                .collect(Collectors.toSet())
                );
            }
        } catch (Exception e) {
            LoggerUtils.consoleLog.error("DatabaseTask - Failed to put words into database: {}", e.toString());
            LoggerUtils.debugLog.error("DatabaseTask - Failed to put words into database:", e);
            return false;
        } finally {
            LoggerUtils.debugLog.info("Database task completed");
        }
    }
}