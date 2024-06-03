package com.example.routefiltering.utils;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.routefiltering.models.Route;
import com.example.routefiltering.repositories.RouteRepository;
import com.example.routefiltering.services.RouteService;

@Configuration
@EnableScheduling
public class RouteExpirationTask {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private RouteService routeService;

    @Scheduled(fixedRate = 60000)
    public void checkForExpiredRoutes() {
        System.out.println("Checking for expired routes...");
        Date now = new Date();
        List<Route> expiredRoutes = routeRepository.findExpiredRoutes(now);
        for (Route route : expiredRoutes) {
            if (route.getExpiry().before(now)) {
                route.setIsOpen(false);
                // routeService.calculateCostPerSegment(route);
                // for (int i = 0; i < route.getStops().size(); i++) {
                //     route.getStops().get(i).getPassengerId();

                // }
                routeRepository.save(route);
            }
        }
    }

    @Scheduled(fixedRate = 60000)
    public void checkAllRoutes() {

        System.out.println("Checking for start date...");
        List<Route> allRoutes = routeRepository.findAll();
        Date now = new Date();
        for (Route route : allRoutes) {
            if(route.getRouteDate().before(now)){

                // route.setIsOpen(false);
                // routeRepository.save(route);
                System.out.println(route);

            }
        }
    }

    
    // @Scheduled(fixedRate = 60000)
    // public void checkAllRoutes() {

    //     System.out.println("Checking for seats available...");
    //     List<Route> allRoutes = routeRepository.findAll();

    //     for (Route route : allRoutes) {

    //         if(route.getSeatsAvailable() < 1){

    //             route.setIsOpen(false);
    //             routeRepository.save(route);

    //         }
    //     }
    // }

}

