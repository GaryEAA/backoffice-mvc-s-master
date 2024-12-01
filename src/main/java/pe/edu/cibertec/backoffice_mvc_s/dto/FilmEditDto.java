package pe.edu.cibertec.backoffice_mvc_s.dto;

import java.util.Date;

public record FilmEditDto(Integer filmId,
                          String title,
                          String description,
                          Integer releaseYear,
                          Integer languageId,
                          String languageName,
                          Integer rentalDuration,
                          Double rentalRate,
                          Integer length,
                          Double replacementCost,
                          String rating,
                          String specialFeatures,
                          Date lastUpdate) {
}
