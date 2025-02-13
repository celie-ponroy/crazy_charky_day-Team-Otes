package outils;

import java.util.UUID;

public record Besoin(UUID id, String nom, Competence competence) {
}
