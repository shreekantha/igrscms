package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.ClassTimeTable;
import com.myriadquest.kreiscms.domain.User;
import com.myriadquest.kreiscms.domain.AcademicCalendar;
import com.myriadquest.kreiscms.domain.Degree;
import com.myriadquest.kreiscms.domain.Department;
import com.myriadquest.kreiscms.domain.Term;
import com.myriadquest.kreiscms.domain.Course;
import com.myriadquest.kreiscms.domain.Period;
import com.myriadquest.kreiscms.repository.ClassTimeTableRepository;
import com.myriadquest.kreiscms.service.ClassTimeTableService;
import com.myriadquest.kreiscms.service.dto.ClassTimeTableDTO;
import com.myriadquest.kreiscms.service.mapper.ClassTimeTableMapper;
import com.myriadquest.kreiscms.service.dto.ClassTimeTableCriteria;
import com.myriadquest.kreiscms.service.ClassTimeTableQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.myriadquest.kreiscms.domain.enumeration.Day;
/**
 * Integration tests for the {@link ClassTimeTableResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ClassTimeTableResourceIT {

    private static final Day DEFAULT_DAY = Day.MON;
    private static final Day UPDATED_DAY = Day.TUE;

    private static final String DEFAULT_TENANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_ID = "BBBBBBBBBB";

    @Autowired
    private ClassTimeTableRepository classTimeTableRepository;

    @Autowired
    private ClassTimeTableMapper classTimeTableMapper;

    @Autowired
    private ClassTimeTableService classTimeTableService;

    @Autowired
    private ClassTimeTableQueryService classTimeTableQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClassTimeTableMockMvc;

    private ClassTimeTable classTimeTable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassTimeTable createEntity(EntityManager em) {
        ClassTimeTable classTimeTable = new ClassTimeTable()
            .day(DEFAULT_DAY)
            .tenantId(DEFAULT_TENANT_ID);
        return classTimeTable;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassTimeTable createUpdatedEntity(EntityManager em) {
        ClassTimeTable classTimeTable = new ClassTimeTable()
            .day(UPDATED_DAY)
            .tenantId(UPDATED_TENANT_ID);
        return classTimeTable;
    }

    @BeforeEach
    public void initTest() {
        classTimeTable = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassTimeTable() throws Exception {
        int databaseSizeBeforeCreate = classTimeTableRepository.findAll().size();
        // Create the ClassTimeTable
        ClassTimeTableDTO classTimeTableDTO = classTimeTableMapper.toDto(classTimeTable);
        restClassTimeTableMockMvc.perform(post("/api/class-time-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classTimeTableDTO)))
            .andExpect(status().isCreated());

        // Validate the ClassTimeTable in the database
        List<ClassTimeTable> classTimeTableList = classTimeTableRepository.findAll();
        assertThat(classTimeTableList).hasSize(databaseSizeBeforeCreate + 1);
        ClassTimeTable testClassTimeTable = classTimeTableList.get(classTimeTableList.size() - 1);
        assertThat(testClassTimeTable.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testClassTimeTable.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createClassTimeTableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classTimeTableRepository.findAll().size();

        // Create the ClassTimeTable with an existing ID
        classTimeTable.setId(1L);
        ClassTimeTableDTO classTimeTableDTO = classTimeTableMapper.toDto(classTimeTable);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassTimeTableMockMvc.perform(post("/api/class-time-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classTimeTableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassTimeTable in the database
        List<ClassTimeTable> classTimeTableList = classTimeTableRepository.findAll();
        assertThat(classTimeTableList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDayIsRequired() throws Exception {
        int databaseSizeBeforeTest = classTimeTableRepository.findAll().size();
        // set the field null
        classTimeTable.setDay(null);

        // Create the ClassTimeTable, which fails.
        ClassTimeTableDTO classTimeTableDTO = classTimeTableMapper.toDto(classTimeTable);


        restClassTimeTableMockMvc.perform(post("/api/class-time-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classTimeTableDTO)))
            .andExpect(status().isBadRequest());

        List<ClassTimeTable> classTimeTableList = classTimeTableRepository.findAll();
        assertThat(classTimeTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClassTimeTables() throws Exception {
        // Initialize the database
        classTimeTableRepository.saveAndFlush(classTimeTable);

        // Get all the classTimeTableList
        restClassTimeTableMockMvc.perform(get("/api/class-time-tables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classTimeTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));
    }
    
    @Test
    @Transactional
    public void getClassTimeTable() throws Exception {
        // Initialize the database
        classTimeTableRepository.saveAndFlush(classTimeTable);

        // Get the classTimeTable
        restClassTimeTableMockMvc.perform(get("/api/class-time-tables/{id}", classTimeTable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classTimeTable.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY.toString()))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID));
    }


    @Test
    @Transactional
    public void getClassTimeTablesByIdFiltering() throws Exception {
        // Initialize the database
        classTimeTableRepository.saveAndFlush(classTimeTable);

        Long id = classTimeTable.getId();

        defaultClassTimeTableShouldBeFound("id.equals=" + id);
        defaultClassTimeTableShouldNotBeFound("id.notEquals=" + id);

        defaultClassTimeTableShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClassTimeTableShouldNotBeFound("id.greaterThan=" + id);

        defaultClassTimeTableShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClassTimeTableShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllClassTimeTablesByDayIsEqualToSomething() throws Exception {
        // Initialize the database
        classTimeTableRepository.saveAndFlush(classTimeTable);

        // Get all the classTimeTableList where day equals to DEFAULT_DAY
        defaultClassTimeTableShouldBeFound("day.equals=" + DEFAULT_DAY);

        // Get all the classTimeTableList where day equals to UPDATED_DAY
        defaultClassTimeTableShouldNotBeFound("day.equals=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    public void getAllClassTimeTablesByDayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classTimeTableRepository.saveAndFlush(classTimeTable);

        // Get all the classTimeTableList where day not equals to DEFAULT_DAY
        defaultClassTimeTableShouldNotBeFound("day.notEquals=" + DEFAULT_DAY);

        // Get all the classTimeTableList where day not equals to UPDATED_DAY
        defaultClassTimeTableShouldBeFound("day.notEquals=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    public void getAllClassTimeTablesByDayIsInShouldWork() throws Exception {
        // Initialize the database
        classTimeTableRepository.saveAndFlush(classTimeTable);

        // Get all the classTimeTableList where day in DEFAULT_DAY or UPDATED_DAY
        defaultClassTimeTableShouldBeFound("day.in=" + DEFAULT_DAY + "," + UPDATED_DAY);

        // Get all the classTimeTableList where day equals to UPDATED_DAY
        defaultClassTimeTableShouldNotBeFound("day.in=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    public void getAllClassTimeTablesByDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        classTimeTableRepository.saveAndFlush(classTimeTable);

        // Get all the classTimeTableList where day is not null
        defaultClassTimeTableShouldBeFound("day.specified=true");

        // Get all the classTimeTableList where day is null
        defaultClassTimeTableShouldNotBeFound("day.specified=false");
    }

    @Test
    @Transactional
    public void getAllClassTimeTablesByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        classTimeTableRepository.saveAndFlush(classTimeTable);

        // Get all the classTimeTableList where tenantId equals to DEFAULT_TENANT_ID
        defaultClassTimeTableShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the classTimeTableList where tenantId equals to UPDATED_TENANT_ID
        defaultClassTimeTableShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllClassTimeTablesByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classTimeTableRepository.saveAndFlush(classTimeTable);

        // Get all the classTimeTableList where tenantId not equals to DEFAULT_TENANT_ID
        defaultClassTimeTableShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the classTimeTableList where tenantId not equals to UPDATED_TENANT_ID
        defaultClassTimeTableShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllClassTimeTablesByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        classTimeTableRepository.saveAndFlush(classTimeTable);

        // Get all the classTimeTableList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultClassTimeTableShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the classTimeTableList where tenantId equals to UPDATED_TENANT_ID
        defaultClassTimeTableShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllClassTimeTablesByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        classTimeTableRepository.saveAndFlush(classTimeTable);

        // Get all the classTimeTableList where tenantId is not null
        defaultClassTimeTableShouldBeFound("tenantId.specified=true");

        // Get all the classTimeTableList where tenantId is null
        defaultClassTimeTableShouldNotBeFound("tenantId.specified=false");
    }
                @Test
    @Transactional
    public void getAllClassTimeTablesByTenantIdContainsSomething() throws Exception {
        // Initialize the database
        classTimeTableRepository.saveAndFlush(classTimeTable);

        // Get all the classTimeTableList where tenantId contains DEFAULT_TENANT_ID
        defaultClassTimeTableShouldBeFound("tenantId.contains=" + DEFAULT_TENANT_ID);

        // Get all the classTimeTableList where tenantId contains UPDATED_TENANT_ID
        defaultClassTimeTableShouldNotBeFound("tenantId.contains=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllClassTimeTablesByTenantIdNotContainsSomething() throws Exception {
        // Initialize the database
        classTimeTableRepository.saveAndFlush(classTimeTable);

        // Get all the classTimeTableList where tenantId does not contain DEFAULT_TENANT_ID
        defaultClassTimeTableShouldNotBeFound("tenantId.doesNotContain=" + DEFAULT_TENANT_ID);

        // Get all the classTimeTableList where tenantId does not contain UPDATED_TENANT_ID
        defaultClassTimeTableShouldBeFound("tenantId.doesNotContain=" + UPDATED_TENANT_ID);
    }


    @Test
    @Transactional
    public void getAllClassTimeTablesByFacultyIsEqualToSomething() throws Exception {
        // Initialize the database
        classTimeTableRepository.saveAndFlush(classTimeTable);
        User faculty = UserResourceIT.createEntity(em);
        em.persist(faculty);
        em.flush();
        classTimeTable.setFaculty(faculty);
        classTimeTableRepository.saveAndFlush(classTimeTable);
        Long facultyId = faculty.getId();

        // Get all the classTimeTableList where faculty equals to facultyId
        defaultClassTimeTableShouldBeFound("facultyId.equals=" + facultyId);

        // Get all the classTimeTableList where faculty equals to facultyId + 1
        defaultClassTimeTableShouldNotBeFound("facultyId.equals=" + (facultyId + 1));
    }


    @Test
    @Transactional
    public void getAllClassTimeTablesByAcademicCalendarIsEqualToSomething() throws Exception {
        // Initialize the database
        classTimeTableRepository.saveAndFlush(classTimeTable);
        AcademicCalendar academicCalendar = AcademicCalendarResourceIT.createEntity(em);
        em.persist(academicCalendar);
        em.flush();
        classTimeTable.setAcademicCalendar(academicCalendar);
        classTimeTableRepository.saveAndFlush(classTimeTable);
        Long academicCalendarId = academicCalendar.getId();

        // Get all the classTimeTableList where academicCalendar equals to academicCalendarId
        defaultClassTimeTableShouldBeFound("academicCalendarId.equals=" + academicCalendarId);

        // Get all the classTimeTableList where academicCalendar equals to academicCalendarId + 1
        defaultClassTimeTableShouldNotBeFound("academicCalendarId.equals=" + (academicCalendarId + 1));
    }


    @Test
    @Transactional
    public void getAllClassTimeTablesByDegreeIsEqualToSomething() throws Exception {
        // Initialize the database
        classTimeTableRepository.saveAndFlush(classTimeTable);
        Degree degree = DegreeResourceIT.createEntity(em);
        em.persist(degree);
        em.flush();
        classTimeTable.setDegree(degree);
        classTimeTableRepository.saveAndFlush(classTimeTable);
        Long degreeId = degree.getId();

        // Get all the classTimeTableList where degree equals to degreeId
        defaultClassTimeTableShouldBeFound("degreeId.equals=" + degreeId);

        // Get all the classTimeTableList where degree equals to degreeId + 1
        defaultClassTimeTableShouldNotBeFound("degreeId.equals=" + (degreeId + 1));
    }


    @Test
    @Transactional
    public void getAllClassTimeTablesByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        classTimeTableRepository.saveAndFlush(classTimeTable);
        Department department = DepartmentResourceIT.createEntity(em);
        em.persist(department);
        em.flush();
        classTimeTable.setDepartment(department);
        classTimeTableRepository.saveAndFlush(classTimeTable);
        Long departmentId = department.getId();

        // Get all the classTimeTableList where department equals to departmentId
        defaultClassTimeTableShouldBeFound("departmentId.equals=" + departmentId);

        // Get all the classTimeTableList where department equals to departmentId + 1
        defaultClassTimeTableShouldNotBeFound("departmentId.equals=" + (departmentId + 1));
    }


    @Test
    @Transactional
    public void getAllClassTimeTablesByTermIsEqualToSomething() throws Exception {
        // Initialize the database
        classTimeTableRepository.saveAndFlush(classTimeTable);
        Term term = TermResourceIT.createEntity(em);
        em.persist(term);
        em.flush();
        classTimeTable.setTerm(term);
        classTimeTableRepository.saveAndFlush(classTimeTable);
        Long termId = term.getId();

        // Get all the classTimeTableList where term equals to termId
        defaultClassTimeTableShouldBeFound("termId.equals=" + termId);

        // Get all the classTimeTableList where term equals to termId + 1
        defaultClassTimeTableShouldNotBeFound("termId.equals=" + (termId + 1));
    }


    @Test
    @Transactional
    public void getAllClassTimeTablesByCourseIsEqualToSomething() throws Exception {
        // Initialize the database
        classTimeTableRepository.saveAndFlush(classTimeTable);
        Course course = CourseResourceIT.createEntity(em);
        em.persist(course);
        em.flush();
        classTimeTable.setCourse(course);
        classTimeTableRepository.saveAndFlush(classTimeTable);
        Long courseId = course.getId();

        // Get all the classTimeTableList where course equals to courseId
        defaultClassTimeTableShouldBeFound("courseId.equals=" + courseId);

        // Get all the classTimeTableList where course equals to courseId + 1
        defaultClassTimeTableShouldNotBeFound("courseId.equals=" + (courseId + 1));
    }


    @Test
    @Transactional
    public void getAllClassTimeTablesByPeriodIsEqualToSomething() throws Exception {
        // Initialize the database
        classTimeTableRepository.saveAndFlush(classTimeTable);
        Period period = PeriodResourceIT.createEntity(em);
        em.persist(period);
        em.flush();
        classTimeTable.setPeriod(period);
        classTimeTableRepository.saveAndFlush(classTimeTable);
        Long periodId = period.getId();

        // Get all the classTimeTableList where period equals to periodId
        defaultClassTimeTableShouldBeFound("periodId.equals=" + periodId);

        // Get all the classTimeTableList where period equals to periodId + 1
        defaultClassTimeTableShouldNotBeFound("periodId.equals=" + (periodId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClassTimeTableShouldBeFound(String filter) throws Exception {
        restClassTimeTableMockMvc.perform(get("/api/class-time-tables?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classTimeTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));

        // Check, that the count call also returns 1
        restClassTimeTableMockMvc.perform(get("/api/class-time-tables/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClassTimeTableShouldNotBeFound(String filter) throws Exception {
        restClassTimeTableMockMvc.perform(get("/api/class-time-tables?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClassTimeTableMockMvc.perform(get("/api/class-time-tables/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingClassTimeTable() throws Exception {
        // Get the classTimeTable
        restClassTimeTableMockMvc.perform(get("/api/class-time-tables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassTimeTable() throws Exception {
        // Initialize the database
        classTimeTableRepository.saveAndFlush(classTimeTable);

        int databaseSizeBeforeUpdate = classTimeTableRepository.findAll().size();

        // Update the classTimeTable
        ClassTimeTable updatedClassTimeTable = classTimeTableRepository.findById(classTimeTable.getId()).get();
        // Disconnect from session so that the updates on updatedClassTimeTable are not directly saved in db
        em.detach(updatedClassTimeTable);
        updatedClassTimeTable
            .day(UPDATED_DAY)
            .tenantId(UPDATED_TENANT_ID);
        ClassTimeTableDTO classTimeTableDTO = classTimeTableMapper.toDto(updatedClassTimeTable);

        restClassTimeTableMockMvc.perform(put("/api/class-time-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classTimeTableDTO)))
            .andExpect(status().isOk());

        // Validate the ClassTimeTable in the database
        List<ClassTimeTable> classTimeTableList = classTimeTableRepository.findAll();
        assertThat(classTimeTableList).hasSize(databaseSizeBeforeUpdate);
        ClassTimeTable testClassTimeTable = classTimeTableList.get(classTimeTableList.size() - 1);
        assertThat(testClassTimeTable.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testClassTimeTable.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingClassTimeTable() throws Exception {
        int databaseSizeBeforeUpdate = classTimeTableRepository.findAll().size();

        // Create the ClassTimeTable
        ClassTimeTableDTO classTimeTableDTO = classTimeTableMapper.toDto(classTimeTable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassTimeTableMockMvc.perform(put("/api/class-time-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classTimeTableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassTimeTable in the database
        List<ClassTimeTable> classTimeTableList = classTimeTableRepository.findAll();
        assertThat(classTimeTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClassTimeTable() throws Exception {
        // Initialize the database
        classTimeTableRepository.saveAndFlush(classTimeTable);

        int databaseSizeBeforeDelete = classTimeTableRepository.findAll().size();

        // Delete the classTimeTable
        restClassTimeTableMockMvc.perform(delete("/api/class-time-tables/{id}", classTimeTable.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClassTimeTable> classTimeTableList = classTimeTableRepository.findAll();
        assertThat(classTimeTableList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
