package com.myriadquest.kreiscms.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.myriadquest.kreiscms.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.myriadquest.kreiscms.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.myriadquest.kreiscms.domain.User.class.getName());
            createCache(cm, com.myriadquest.kreiscms.domain.Authority.class.getName());
            createCache(cm, com.myriadquest.kreiscms.domain.User.class.getName() + ".authorities");
            createCache(cm, com.myriadquest.kreiscms.domain.Institute.class.getName());
            createCache(cm, com.myriadquest.kreiscms.domain.ContactDetails.class.getName());
            createCache(cm, com.myriadquest.kreiscms.domain.News.class.getName());
            createCache(cm, com.myriadquest.kreiscms.domain.Term.class.getName());
            createCache(cm, com.myriadquest.kreiscms.domain.GalleryCat.class.getName());
            createCache(cm, com.myriadquest.kreiscms.domain.GalleryCat.class.getName() + ".galleries");
            createCache(cm, com.myriadquest.kreiscms.domain.Gallery.class.getName());
            createCache(cm, com.myriadquest.kreiscms.domain.AboutUs.class.getName());
            createCache(cm, com.myriadquest.kreiscms.domain.SpeakerDesk.class.getName());
            createCache(cm, com.myriadquest.kreiscms.domain.Vision.class.getName());
            createCache(cm, com.myriadquest.kreiscms.domain.Mission.class.getName());
            createCache(cm, com.myriadquest.kreiscms.domain.Profile.class.getName());
            createCache(cm, com.myriadquest.kreiscms.domain.NoticeBoard.class.getName());
            createCache(cm, com.myriadquest.kreiscms.domain.HomeImg.class.getName());
            createCache(cm, com.myriadquest.kreiscms.domain.Degree.class.getName());
            createCache(cm, com.myriadquest.kreiscms.domain.Degree.class.getName() + ".departments");
            createCache(cm, com.myriadquest.kreiscms.domain.Degree.class.getName() + ".academicCalendars");
            createCache(cm, com.myriadquest.kreiscms.domain.Degree.class.getName() + ".classTimeTables");
            createCache(cm, com.myriadquest.kreiscms.domain.Degree.class.getName() + ".examTimeTables");
            createCache(cm, com.myriadquest.kreiscms.domain.Scheme.class.getName());
            createCache(cm, com.myriadquest.kreiscms.domain.Department.class.getName());
            createCache(cm, com.myriadquest.kreiscms.domain.Department.class.getName() + ".classTimeTables");
            createCache(cm, com.myriadquest.kreiscms.domain.Department.class.getName() + ".examTimeTables");
            createCache(cm, com.myriadquest.kreiscms.domain.Term.class.getName() + ".classTimeTables");
            createCache(cm, com.myriadquest.kreiscms.domain.Term.class.getName() + ".examTimeTables");
            createCache(cm, com.myriadquest.kreiscms.domain.AcademicCalendar.class.getName());
            createCache(cm, com.myriadquest.kreiscms.domain.AcademicCalendar.class.getName() + ".classTimeTables");
            createCache(cm, com.myriadquest.kreiscms.domain.AcademicCalendar.class.getName() + ".examTimeTables");
            createCache(cm, com.myriadquest.kreiscms.domain.Course.class.getName());
            createCache(cm, com.myriadquest.kreiscms.domain.Course.class.getName() + ".classTimeTables");
            createCache(cm, com.myriadquest.kreiscms.domain.Course.class.getName() + ".examTimeTables");
            createCache(cm, com.myriadquest.kreiscms.domain.Period.class.getName());
            createCache(cm, com.myriadquest.kreiscms.domain.Period.class.getName() + ".classTimeTables");
            createCache(cm, com.myriadquest.kreiscms.domain.ClassTimeTableConfig.class.getName());
            createCache(cm, com.myriadquest.kreiscms.domain.ClassTimeTable.class.getName());
            createCache(cm, com.myriadquest.kreiscms.domain.ExamTimeTable.class.getName());
            createCache(cm, com.myriadquest.kreiscms.domain.UserProfile.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
