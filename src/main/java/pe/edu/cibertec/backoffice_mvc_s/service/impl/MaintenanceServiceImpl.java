package pe.edu.cibertec.backoffice_mvc_s.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmDetailDto;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmDto;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmEditDto;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmInsertDto;
import pe.edu.cibertec.backoffice_mvc_s.entity.Film;
import pe.edu.cibertec.backoffice_mvc_s.entity.Language;
import pe.edu.cibertec.backoffice_mvc_s.repository.FilmRepository;
import pe.edu.cibertec.backoffice_mvc_s.repository.LanguageRepository;
import pe.edu.cibertec.backoffice_mvc_s.service.MaintenanceService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    LanguageRepository languageRepository;

    @Override
    public List<FilmDto> getAllFilms() {

        List<FilmDto> films = new ArrayList<FilmDto>();
        Iterable<Film> iterable = filmRepository.findAll();
        iterable.forEach(film -> {
            FilmDto filmDto = new FilmDto(film.getFilmId(),
                    film.getTitle(),
                    film.getLanguage().getName(),
                    film.getRentalRate());
            films.add(filmDto);
        });

        return films;
    }

    @Override
    public FilmDetailDto getFilmById(int id) {

        Optional<Film> optional = filmRepository.findById(id);
        return optional.map(
                film -> new FilmDetailDto(film.getFilmId(),
                        film.getTitle(),
                        film.getDescription(),
                        film.getReleaseYear(),
                        film.getLanguage().getLanguageId(),
                        film.getLanguage().getName(),
                        film.getRentalDuration(),
                        film.getRentalRate(),
                        film.getLength(),
                        film.getReplacementCost(),
                        film.getRating(),
                        film.getSpecialFeatures(),
                        film.getLastUpdate())
        ).orElse(null);
    }

    @Override
    public FilmEditDto getFilmForEditById(int id) {

        Optional<Film> optional = filmRepository.findById(id);

        return optional.map(film -> new FilmEditDto(
                film.getFilmId(),
                film.getTitle(),
                film.getDescription(),
                film.getReleaseYear(),
                film.getLanguage().getLanguageId(),
                film.getLanguage().getName(),
                film.getRentalDuration(),
                film.getRentalRate(),
                film.getLength(),
                film.getReplacementCost(),
                film.getRating(),
                film.getSpecialFeatures(),
                film.getLastUpdate()
        )).orElse(null);
    }

    @Override
    public void editFilm(FilmEditDto filmEditDto) {
        Film film = filmRepository.findById(filmEditDto.filmId())
                .orElseThrow(() -> new RuntimeException("Film not found"));

        Language language = languageRepository.findById(filmEditDto.languageId())
                .orElseThrow(() -> new RuntimeException("Language not found"));

        film.setTitle(filmEditDto.title());
        film.setDescription(filmEditDto.description());
        film.setReleaseYear(filmEditDto.releaseYear());
        film.setLanguage(language);
        film.setRentalDuration(filmEditDto.rentalDuration());
        film.setRentalRate(filmEditDto.rentalRate());
        film.setLength(filmEditDto.length());
        film.setReplacementCost(filmEditDto.replacementCost());
        film.setRating(filmEditDto.rating());
        film.setSpecialFeatures(filmEditDto.specialFeatures());
        film.setLastUpdate(new Date());

        filmRepository.save(film);
    }

    @Override
    public List<Language> getAllLanguages() {
        return (List<Language>) languageRepository.findAll();
    }


    @Override
    public void insertFilm(FilmInsertDto filmInsertDto) {
        Film film = new Film();
        film.setTitle(filmInsertDto.title());
        film.setDescription(filmInsertDto.description());
        film.setReleaseYear(filmInsertDto.releaseYear());
        film.setRentalDuration(filmInsertDto.rentalDuration());
        film.setRentalRate(filmInsertDto.rentalRate());
        film.setLength(filmInsertDto.length());
        film.setReplacementCost(filmInsertDto.replacementCost());
        film.setRating(filmInsertDto.rating());
        film.setSpecialFeatures(filmInsertDto.specialFeatures());

        Language language = languageRepository.findById(filmInsertDto.languageId())
                .orElseThrow(() -> new IllegalArgumentException("Idioma no válido"));
        film.setLanguage(language);
        film.setLastUpdate(new Date());

        filmRepository.save(film);
    }

    @Override
    public void deleteFilmById(int id) {
        filmRepository.deleteById(id);
    }

}
