package com.example.campus.config;

import com.example.campus.entity.Event;
import com.example.campus.entity.Admin;
import com.example.campus.repository.AdminRepository;
import com.example.campus.repository.EventRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(EventRepository eventRepository, AdminRepository adminRepository) {
        return args -> {
            // Seed default admin if none exists
            if (adminRepository.count() == 0) {
                Admin admin = new Admin();
                admin.setUsername("admin");
                admin.setPassword("admin123");
                adminRepository.save(admin);
            }
            // Only seed data if the events table is empty
            if (eventRepository.count() == 0) {
                // Event 1
                Event e1 = new Event();
                e1.setTitle("Annual Tech Symposium");
                e1.setDescription("A massive gathering of tech enthusiasts featuring hackathons, coding challenges, and keynote speeches from industry leaders.");
                e1.setImageUrl("https://images.unsplash.com/photo-1540575467063-178a50c2df87?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80");
                e1.setDate("2026-05-15");
                e1.setDay("Friday");
                e1.setTime("09:00");
                eventRepository.save(e1);

                // Event 2
                Event e2 = new Event();
                e2.setTitle("Spring Cultural Fest 2026");
                e2.setDescription("Celebrate diversity with music, dance, theater, and incredible food from different cultures from around the world.");
                e2.setImageUrl("https://images.unsplash.com/photo-1459749411175-04bf5292ceea?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80");
                e2.setDate("2026-05-22");
                e2.setDay("Friday");
                e2.setTime("17:00");
                eventRepository.save(e2);

                // Event 3
                Event e3 = new Event();
                e3.setTitle("AI & Machine Learning Workshop");
                e3.setDescription("A hands-on workshop building your first neural network. Laptops are required! Great for beginners.");
                e3.setImageUrl("https://images.unsplash.com/photo-1555949963-ff9fe0c870eb?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80");
                e3.setDate("2026-06-05");
                e3.setDay("Friday");
                e3.setTime("10:00");
                eventRepository.save(e3);

                // Event 4
                Event e4 = new Event();
                e4.setTitle("Inter-College Sports Meet");
                e4.setDescription("Watch or participate in track and field, basketball, and football tournaments against rival universities.");
                e4.setImageUrl("https://images.unsplash.com/photo-1461896836934-ffe607ba8211?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80");
                e4.setDate("2026-06-12");
                e4.setDay("Friday");
                e4.setTime("08:00");
                eventRepository.save(e4);

                // Event 5
                Event e5 = new Event();
                e5.setTitle("Global Career Fair");
                e5.setDescription("Meet recruiters from top tech, finance, and engineering firms looking to hire interns and fresh graduates. Dress strictly formal.");
                e5.setImageUrl("https://images.unsplash.com/photo-1542744173-8e7e53415bb0?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80");
                e5.setDate("2026-06-15");
                e5.setDay("Monday");
                e5.setTime("11:00");
                eventRepository.save(e5);

                // Event 6
                Event e6 = new Event();
                e6.setTitle("Campus Art Exhibition");
                e6.setDescription("View phenomenal creative artworks, sculptures, and digital art created entirely by our talented student body.");
                e6.setImageUrl("https://images.unsplash.com/photo-1460661419201-fd4cecdf8a8b?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80");
                e6.setDate("2026-06-24");
                e6.setDay("Wednesday");
                e6.setTime("14:00");
                eventRepository.save(e6);

                // Event 7
                Event e7 = new Event();
                e7.setTitle("Entrepreneurship Summit");
                e7.setDescription("Listen to successful startup founders share their journeys, pitch your own ideas, and find potential co-founders.");
                e7.setImageUrl("https://images.unsplash.com/photo-1556761175-5973e04eab99?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80");
                e7.setDate("2026-07-02");
                e7.setDay("Thursday");
                e7.setTime("13:00");
                eventRepository.save(e7);

                // Event 8
                Event e8 = new Event();
                e8.setTitle("Esports Gaming Tournament");
                e8.setDescription("Compete in Valorant, League of Legends, and FIFA. Massive prize pool up for grabs! Bring your own peripherals.");
                e8.setImageUrl("https://images.unsplash.com/photo-1542751371-adc38448a05e?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80");
                e8.setDate("2026-07-10");
                e8.setDay("Friday");
                e8.setTime("18:00");
                eventRepository.save(e8);

                // Event 9
                Event e9 = new Event();
                e9.setTitle("Robotics Showcase");
                e9.setDescription("Witness autonomous drones and combat rovers built by the engineering department in full action in the central courtyard.");
                e9.setImageUrl("https://images.unsplash.com/photo-1485827404703-89b55fcc595e?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80");
                e9.setDate("2026-07-20");
                e9.setDay("Monday");
                e9.setTime("09:30");
                eventRepository.save(e9);

                // Event 10
                Event e10 = new Event();
                e10.setTitle("Campus Bonfire & Stargazing");
                e10.setDescription("Relax before finals week with a huge bonfire, free marshmallows, and telescopes setup by the Astronomy club.");
                e10.setImageUrl("https://images.unsplash.com/photo-1504548840739-580b10ae7715?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80");
                e10.setDate("2026-07-25");
                e10.setDay("Saturday");
                e10.setTime("20:00");
                eventRepository.save(e10);
            }
        };
    }
}
