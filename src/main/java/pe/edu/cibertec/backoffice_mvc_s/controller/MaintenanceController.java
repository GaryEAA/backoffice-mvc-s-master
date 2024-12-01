package pe.edu.cibertec.backoffice_mvc_s.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmDetailDto;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmDto;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmEditDto;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmInsertDto;
import pe.edu.cibertec.backoffice_mvc_s.entity.Film;
import pe.edu.cibertec.backoffice_mvc_s.entity.Language;
import pe.edu.cibertec.backoffice_mvc_s.service.MaintenanceService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceController {

    @Autowired
    MaintenanceService maintenanceService;

    @GetMapping("/start")
    public String start(Model model) {

        List<FilmDto> films = maintenanceService.getAllFilms();
        model.addAttribute("films", films);
        return "maintenance";

    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {

        FilmDetailDto filmDetailDto = maintenanceService.getFilmById(id);
        model.addAttribute("filmDetailDto", filmDetailDto);
        return "maintenance-detail";

    }

    @GetMapping("/edit/{id}")
    public String editFilmForm(@PathVariable Integer id, Model model) {
        FilmEditDto filmEditDto = maintenanceService.getFilmForEditById(id);
        List<Language> languages = maintenanceService.getAllLanguages();

        List<String> specialFeaturesList = Arrays.asList(filmEditDto.specialFeatures().split(",\\s*"));

        model.addAttribute("filmEditDto", filmEditDto);
        model.addAttribute("languages", languages);
        model.addAttribute("specialFeaturesList", specialFeaturesList);

        return "maintenance-edit";
    }

    @PostMapping("/edit")
    public String editFilm(FilmEditDto filmEditDto) {
        String specialFeatures = String.join(", ", filmEditDto.specialFeatures());
        filmEditDto = new FilmEditDto(
                filmEditDto.filmId(),
                filmEditDto.title(),
                filmEditDto.description(),
                filmEditDto.releaseYear(),
                filmEditDto.languageId(),
                filmEditDto.languageName(),
                filmEditDto.rentalDuration(),
                filmEditDto.rentalRate(),
                filmEditDto.length(),
                filmEditDto.replacementCost(),
                filmEditDto.rating(),
                specialFeatures,
                filmEditDto.lastUpdate()
        );
        maintenanceService.editFilm(filmEditDto);
        return "redirect:/maintenance/start";
    }

    @GetMapping("/insert")
    public String insertFilmForm(Model model) {
        FilmInsertDto filmInsertDto = new FilmInsertDto(
                "",
                "",
                null,
                null,
                "",
                0,
                0.0,
                null,
                0.0,
                "",
                "",
                new Date()
        );

        List<Language> languages = maintenanceService.getAllLanguages();

        model.addAttribute("filmInsertDto", filmInsertDto);
        model.addAttribute("languages", languages);

        return "maintenance-insert";
    }

    @PostMapping("/insert")
    public String insertFilm(FilmInsertDto filmInsertDto) {
        maintenanceService.insertFilm(filmInsertDto);
        return "redirect:/maintenance/start";
    }

    @GetMapping("/delete/{id}")
    public String deleteFilm(@PathVariable Integer id) {
        maintenanceService.deleteFilmById(id);
        return "redirect:/maintenance/start";
    }
}
