package com.zinco.react_back.controller;

import com.zinco.react_back.entity.Recommend;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-unit");

    @PostMapping("/recommend")
    public String recommendMusic(@ModelAttribute Recommend recommend) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(recommend);
        em.getTransaction().commit();
        em.close();
        return "redirect:/admin/recommendations";
    }

    @GetMapping("/recommendations")
    public String viewRecommendations(Model model) {
        EntityManager em = emf.createEntityManager();
        List<Recommend> recommendations = em.createQuery("FROM Recommend", Recommend.class).getResultList();
        model.addAttribute("recommendations", recommendations);
        em.close();
        return "recommendationsView";
    }
}