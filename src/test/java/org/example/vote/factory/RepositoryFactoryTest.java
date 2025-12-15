package org.example.vote.factory;

import org.example.vote.repo.InMemoryVoteRepository;
import org.example.vote.repo.VoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryFactoryTest {

    @Test
    void testCreateMemoryRepository() {
        VoteRepository repo = RepositoryFactory.createRepo("memory");
        assertNotNull(repo);
        assertTrue(repo instanceof InMemoryVoteRepository);
    }

    @ParameterizedTest
    @ValueSource(strings = {"unknown", "sql", "file", "db"})
    void testCreateRepositoryWithInvalidType(String type) {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                RepositoryFactory.createRepo(type)
        );
        assertTrue(exception.getMessage().contains("Unknown repo type"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"MeMoRy", "MEMORY", "Memory", "mEmOrY"})
    void testCreateRepositoryCaseInsensitive(String type) {
        VoteRepository repo = RepositoryFactory.createRepo(type);
        assertNotNull(repo);
        assertTrue(repo instanceof InMemoryVoteRepository);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testCreateRepositoryWithNullOrEmpty(String type) {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                RepositoryFactory.createRepo(type)
        );
        assertTrue(exception.getMessage().contains("Unknown repo type"));
    }

    @Test
    void testCreateRepositoryWithWhitespace() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                RepositoryFactory.createRepo("  ")
        );
        assertTrue(exception.getMessage().contains("Unknown repo type"));
    }
    @Test
    void testExceptionMessageContent() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> RepositoryFactory.createRepo("invalid")
        );
        assertEquals("Unknown repo type invalid", exception.getMessage());
    }
}