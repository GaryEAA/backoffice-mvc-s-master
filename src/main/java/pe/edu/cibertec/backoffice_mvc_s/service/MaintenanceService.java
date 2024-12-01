package pe.edu.cibertec.backoffice_mvc_s.service;

import pe.edu.cibertec.backoffice_mvc_s.dto.FilmDetailDto;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmDto;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmEditDto;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmInsertDto;
import pe.edu.cibertec.backoffice_mvc_s.entity.Language;

import java.util.List;
import java.util.Optional;

public interface MaintenanceService {

    List<FilmDto> getAllFilms();

    FilmDetailDto getFilmById(int id);

    FilmEditDto getFilmForEditById(int id);

    void editFilm(FilmEditDto filmEditDto);

    List<Language> getAllLanguages();

    void insertFilm(FilmInsertDto filmInsertDto);

    void deleteFilmById(int id);
}
