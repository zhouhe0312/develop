package cn.powertime.iatp.thread;

import cn.powertime.iatp.entity.BaseExaminationResult;
import cn.powertime.iatp.entity.BaseExaminationResultDetails;
import cn.powertime.iatp.entity.BaseWrongQuestions;
import cn.powertime.iatp.service.BaseExaminationResultDetailsService;
import cn.powertime.iatp.service.BaseExaminationResultService;
import cn.powertime.iatp.service.BaseWrongQuestionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SubmitPaperThread implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubmitPaperThread.class);

    private BaseExaminationResultService baseExaminationResultService;

    private BaseExaminationResultDetailsService baseExaminationResultDetailsService;

    private BaseExaminationResult baseExaminationResult;

    private List<BaseExaminationResultDetails> baseExaminationResultDetailses;

    private BaseWrongQuestionsService baseWrongQuestionsService;

    private List<BaseWrongQuestions> baseWrongQuestionsList;

    private List<BaseWrongQuestions> updateBaseWrongQuestionsList;


    public SubmitPaperThread() {
    }

    public SubmitPaperThread(BaseExaminationResultService baseExaminationResultService, BaseExaminationResultDetailsService baseExaminationResultDetailsService, BaseWrongQuestionsService baseWrongQuestionsService, BaseExaminationResult baseExaminationResult, List<BaseExaminationResultDetails> baseExaminationResultDetailses,List<BaseWrongQuestions> baseWrongQuestionsList, List<BaseWrongQuestions> updateBaseWrongQuestionsList) {
        this.baseExaminationResultService = baseExaminationResultService;
        this.baseExaminationResultDetailsService = baseExaminationResultDetailsService;
        this.baseExaminationResult = baseExaminationResult;
        this.baseExaminationResultDetailses = baseExaminationResultDetailses;
        this.baseWrongQuestionsService =baseWrongQuestionsService;
        this.baseWrongQuestionsList = baseWrongQuestionsList;
        this.updateBaseWrongQuestionsList = updateBaseWrongQuestionsList;
    }

    @Override
    public void run() {
        LOGGER.info("提交试卷线程 ------ start");
        baseExaminationResultService.add(baseExaminationResult);
        baseExaminationResultDetailsService.batchAdd(baseExaminationResultDetailses);
        baseWrongQuestionsService.batchAdd(baseWrongQuestionsList);
        baseWrongQuestionsService.batchUpdate(updateBaseWrongQuestionsList);
        LOGGER.info("提交试卷线程 ------ end");
    }
}
