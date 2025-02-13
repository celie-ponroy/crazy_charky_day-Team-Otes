package outils;

import java.util.Map;
import java.util.UUID;

public record Salarie(UUID id, String nom, Map<Competence, Integer> competences) {
}
