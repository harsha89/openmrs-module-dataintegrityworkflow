/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.dataintegrityworkflow.db.hibernate;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openmrs.User;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.dataintegrity.IntegrityCheck;
import org.openmrs.module.dataintegrity.IntegrityCheckResult;
import org.openmrs.module.dataintegrityworkflow.*;
import org.openmrs.module.dataintegrityworkflow.db.DataIntegrityWorkflowDAO;

import java.util.List;

/**
 * @author: harsz89
 */
public class HibernateDataIntegrityWorkflowDAO implements DataIntegrityWorkflowDAO {
    /**
     * the session factory to use in this DAO
     */
    private SessionFactory sessionFactory;

    /**
     * @see org.openmrs.module.dataintegrityworkflow.db.DataIntegrityWorkflowDAO#setSessionFactory(org.hibernate.SessionFactory)
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @see org.openmrs.module.dataintegrityworkflow.db.DataIntegrityWorkflowDAO#getSessionFactory()
     */
    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    public void saveIntegrityWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord) {
        sessionFactory.getCurrentSession().save(integrityWorkflowRecord);
    }

    public void saveWorkflowStage(WorkflowStage workflowStage) {
        sessionFactory.getCurrentSession().save(workflowStage);
    }

    public void saveIntegrityCheckUpdate(IntegrityCheckUpdate integrityCheckUpdate) throws DAOException {
        sessionFactory.getCurrentSession().save(integrityCheckUpdate);
    }

    public int  saveWorkflowAssignee(RecordAssignee recordAssignee) {
        return  (Integer)sessionFactory.getCurrentSession().save(recordAssignee);
    }

    public void saveIntegrityRecordStageChange(IntegrityRecordStageChange integrityRecordStageChange) {
        sessionFactory.getCurrentSession().save(integrityRecordStageChange);
    }

    public void saveIntegrityRecordComment(IntegrityRecordComment integrityRecordComment) {
        sessionFactory.getCurrentSession().save(integrityRecordComment);
    }

    public int saveIntegrityRecordAssignment(IntegrityRecordAssignment integrityRecordAssignment){
        return (Integer)sessionFactory.getCurrentSession().save(integrityRecordAssignment);
    }

    public void saveIntegrityRecordStatusChange(RecordStatusChange recordStatusChange) {
        sessionFactory.getCurrentSession().save(recordStatusChange);
    }

    public RecordAssignee getRecordAssigneeById(int assigneeId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RecordAssignee.class);
        criteria.add(Restrictions.eq("recordAssigneeId",assigneeId));
        return (RecordAssignee) criteria.uniqueResult();
    }

    public IntegrityCheckUpdate getIntegrityCheckUpdate(int checkId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IntegrityCheckUpdate.class);
        criteria.add(Restrictions.eq("integrityCheckId",checkId));
        return (IntegrityCheckUpdate) criteria.uniqueResult();
    }

    public IntegrityRecordAssignment getIntegrityRecordAssignmentByAssigneeAndId(RecordAssignee recordAssignee,int assginmentId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IntegrityRecordAssignment.class);
        criteria.add(Restrictions.eq("recordAssignee",recordAssignee)).add(Restrictions.eq("assignmentId",assginmentId));
        return (IntegrityRecordAssignment) criteria.uniqueResult();
    }

    public IntegrityWorkflowRecord getIntegrityWorkflowRecord(int integrityRecordWorkflowDetailId) {
        return (IntegrityWorkflowRecord) sessionFactory.getCurrentSession().get(IntegrityWorkflowRecord.class, integrityRecordWorkflowDetailId);
    }

    public List<IntegrityWorkflowRecord> getAllIntegrityWorkflowRecordsForCheck(int checkId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IntegrityWorkflowRecord.class);
        criteria.add(Restrictions.eq("integrityCheckId",checkId));
        return (List<IntegrityWorkflowRecord>) criteria.list();
    }

    public IntegrityWorkflowRecord getAssignedIntegrityWorkflowRecordByAssignee(RecordAssignee assignedUser) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IntegrityWorkflowRecord.class);
        criteria.add(Restrictions.eq("currentAssignee",assignedUser));
        return (IntegrityWorkflowRecord) criteria.uniqueResult();
    }

    public List<RecordAssignee> getAllAssignmentsOfUser(User user) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RecordAssignee.class);
        criteria.add(Restrictions.eq("assignee",user));
        return (List<RecordAssignee>) criteria.list();
    }

    public RecordAssignee getCurrentAssignmentOfUser(User user) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public WorkflowStage getWorkflowStage(int stageId) {
        return (WorkflowStage)sessionFactory.getCurrentSession().get(WorkflowStage.class,stageId);
    }

    public List<WorkflowStage> getWorkflowStages() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(WorkflowStage.class);
        return (List<WorkflowStage>) criteria.list();
    }

    public RecordStatus getRecordStatus(int stageId) {
        return (RecordStatus)sessionFactory.getCurrentSession().get(RecordStatus.class,stageId);
    }

    public List<RecordStatus> getAllRecordStatus() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RecordStatus.class);
        return (List<RecordStatus>) criteria.list();
    }

    public RecordAssignee getWorkflowRecordAssigneeByUserAndWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord, User assignUser) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RecordAssignee.class);
        criteria.add(Restrictions.eq("integrityWorkflowRecord",integrityWorkflowRecord)).add(Restrictions.eq("assignee",assignUser));
        return (RecordAssignee) criteria.uniqueResult();
    }

    public List<IntegrityRecordComment> getIntegrityRecordComments(IntegrityWorkflowRecord integrityWorkflowRecord) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IntegrityRecordComment.class);
        criteria.add(Restrictions.eq("integrityWorkflowRecord",integrityWorkflowRecord));
        return (List<IntegrityRecordComment>) criteria.list();
    }

    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByResult(IntegrityCheckResult integrityCheckResult) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IntegrityWorkflowRecord.class);
        criteria.add(Restrictions.eq("integrityCheckResult",integrityCheckResult));
        List records=criteria.list();
        if(records.size()>0) {
            return (IntegrityWorkflowRecord) records.get(0);
        }
        return null;
    }

    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByResultId(int resultId) {
        return (IntegrityWorkflowRecord) sessionFactory.getCurrentSession().get(IntegrityWorkflowRecord.class, resultId);
    }

    public void updateIntegrityRecordComment(IntegrityRecordComment integrityRecordComment) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateIntegrityWorkflowRecord(IntegrityWorkflowRecord integrityWorkflowRecord) {
        sessionFactory.getCurrentSession().update(integrityWorkflowRecord);
    }

    public void updateWorkflowAssignee(RecordAssignee recordAssignee) {
        sessionFactory.getCurrentSession().update(recordAssignee);
    }

    public void updateCheckUpdate(IntegrityCheckUpdate integrityCheckUpdate) {
        sessionFactory.getCurrentSession().update(integrityCheckUpdate);
    }

    public void updateIntegrityRecordAssignment(IntegrityRecordAssignment integrityRecordAssignment) {
        sessionFactory.getCurrentSession().update(integrityRecordAssignment);
    }

    public void deleteIntegrityRecordComment(IntegrityRecordComment integrityRecordComment) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public IntegrityWorkflowRecord getIntegrityWorkflowRecordByRecordId(int recordId) {
        return (IntegrityWorkflowRecord) sessionFactory.getCurrentSession().get(IntegrityWorkflowRecord.class, recordId);
    }

}
