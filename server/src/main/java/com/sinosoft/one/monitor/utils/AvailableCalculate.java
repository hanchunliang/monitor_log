package com.sinosoft.one.monitor.utils;


import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalTime;
import org.springframework.util.Assert;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 可用性计算
 * User: cq
 * Date: 13-3-5
 * Time: PM12:36
 */
public class AvailableCalculate {

    private long runningTime;

    private List<AvailableCountsGroupByInterval> avCount;

    private  final int interval;

    private long stopTime;

    private long unknownTime;

    private Long oldRunningTime;

    private Long oldStopTime;

    private List<AvailableCountsGroupByInterval> unAvCount;

    //当天时间转换的分钟数
    private long dayMinute;

    private Integer oldFalseCount;

    private Integer falseCount;

    private Boolean currentResult;

    private AvailableInf previousInf;

    /**
     * 可用性计算
     * @param availableCalculateParam 可用性对像参数
     * @return
     */
    public static AvailableCalculate calculate(final AvailableCalculateParam availableCalculateParam){
        return new AvailableCalculate(availableCalculateParam);
    }


    private AvailableCalculate(AvailableCalculateParam availableCalculateParam) {
        Assert.notNull(availableCalculateParam);

        this.interval = availableCalculateParam.getInterval();
        this.avCount = availableCalculateParam.getAvCount();
        this.oldRunningTime = availableCalculateParam.getOldRunningTime();
        this.oldStopTime  = availableCalculateParam.getOldStopTime();
        this.unAvCount = availableCalculateParam.getUnAvCount();
        this.oldFalseCount = availableCalculateParam.getOldFalseCount();
        this.currentResult = availableCalculateParam.isCurrentResult();
        this.previousInf = availableCalculateParam.getPrevious();
        init();
    }

    private void init(){
        timeCalculate();
        runTimeCalculate();
        stopTimeCalculate();
        unknownTimeCalculate();
        falseCountCalculate();
    }

    private void falseCountCalculate() {
        boolean  failureCountNeedAdd = false;
        //本次结果正确，观察与上次的时间差是否满足设置间隔，不满足则认为失败
        if(this.currentResult){
            //如果上次状态成功，且上次间隔时间与这次间隔时间一致,但两次时间差大于设置间隔则代表有问题。此处等待将来linux端改为其他方式后，可以进行调整
            if(previousInf!=null&&previousInf.getStatus()&&previousInf.getInterval().equals(interval)){
                Interval timeInterval = new Interval(new DateTime(previousInf.getRecordDate()),DateTime.now());
	            if(timeInterval.toDuration().getStandardMinutes()>interval){
                    failureCountNeedAdd = true;
                }
            }
        }else{
            //如果上次状态成功，则失败总数要计为+1
            if(previousInf!=null&&previousInf.getStatus()){
                failureCountNeedAdd = true;
            }
            //第一条数据即为空
            else if(previousInf == null||oldFalseCount == 0)
                failureCountNeedAdd = true;
        }

        if(failureCountNeedAdd)
            this.falseCount =  oldFalseCount+1;
        else
            this.falseCount = oldFalseCount;
    }

    private void unknownTimeCalculate() {
        this.unknownTime =  this.dayMinute - this.runningTime - this.stopTime;
    }


    private void runTimeCalculate(){
        if(this.currentResult){
            runningTime = interval;
        }
        for (AvailableCountsGroupByInterval availableDetail : avCount) {
            runningTime = runningTime + availableDetail.getCount() * availableDetail.getInterval();
        }
        //正常运行次数*间隔时间即当天天可用时间
        //runningTime = avCount*interval;
        Assert.isTrue(runningTime >= oldRunningTime, "oldRunningTime is " + oldRunningTime + ",new CalculateRunningTime is " +
                this.runningTime + "can't less than old !");
    }

    private void timeCalculate(){
        LocalTime localTime = LocalTime.now();
        this.dayMinute = localTime.getHourOfDay()*60+localTime.getMinuteOfHour();
    }

    private void stopTimeCalculate(){
        if(unAvCount==null){//并不考虑未知的情况，运行时间剩下的时间即为停止时间
            this.stopTime = this.dayMinute - this.runningTime;
        }else{
            if(!this.currentResult){
                this.stopTime = this.stopTime+interval;
            }
            for(AvailableCountsGroupByInterval availableDetail:unAvCount){
                //失败运行次数*间隔时间即当天停止时间
                this.stopTime = this.stopTime +  availableDetail.getCount()*availableDetail.getInterval();
            }
        }
        Assert.isTrue(stopTime >= oldStopTime, "oldStopTime is " + oldStopTime + ",new CalculateStopTime is " +
                this.stopTime + "can't less than old !");
    }

    /**
     * 获取正常运行时间,以分钟为单位
     * @return
     */
    public BigDecimal getAliveTime(){
        if(runningTime == 0l) {
            runTimeCalculate();
        }
        return new BigDecimal(runningTime);
    }

    /**
     * 获取停止时间，以分钟为单位
     * @return
     */
    public BigDecimal getStopTime(){
        return new BigDecimal(this.stopTime);
    }

    public BigDecimal getUnknownTime(){
        return  new BigDecimal(this.unknownTime);
    }

    /**
     * 获取平均故障时间MTBF
     * @return
     */
    public BigDecimal getTimeBetweenFailures(){
        return  new BigDecimal(runningTime/(falseCount+1));
    }

    /**
     * 获取平均修复时间MTTR
     * @return
     */
    public BigDecimal getTimeToRepair(){
        if(this.falseCount == 0){
            return BigDecimal.ZERO;
        }
        return new BigDecimal(stopTime/this.falseCount);
    }

    public BigDecimal getFalseCount(){
        return new BigDecimal(this.falseCount);
    }

    /**
     * 可用性计算的参数
     */
    public static class AvailableCalculateParam{

        private final Long oldRunningTime;
        private final Long oldStopTime;
        private final Integer oldFalseCount;

        private final List<AvailableCountsGroupByInterval> avCount;
        private final List<AvailableCountsGroupByInterval> unAvCount;

        private final Integer interval;

        private final  boolean currentResult;

        private final AvailableInf previous;

        /**
         * 可用性计算参数对象
         * @param statistics 可用性
         * @param avCount
         * @param unAvCount
         * @param interval
         * @param currentResult
         * @param previous
         */
        public AvailableCalculateParam(AvailableStatistics statistics, List<AvailableCountsGroupByInterval> avCount,
                                       List<AvailableCountsGroupByInterval> unAvCount, Integer interval,
                                       boolean currentResult, AvailableInf previous) {
            Assert.notNull(statistics.getOldRunningTime());
            Assert.notNull(statistics.getOldStopTime());
            Assert.notNull(interval);
            this.oldRunningTime = statistics.getOldRunningTime();
            this.oldStopTime = statistics.getOldStopTime();
            this.avCount = avCount;
            this.unAvCount = unAvCount;
            this.oldFalseCount = statistics.getOldFalseCount();
            this.interval = interval;
            this.currentResult = currentResult;
            this.previous = previous;
        }


        public Long getOldRunningTime() {
            return oldRunningTime;
        }

        public Long getOldStopTime() {
            return oldStopTime;
        }

        public List<AvailableCountsGroupByInterval> getAvCount() {
            return avCount;
        }

        public List<AvailableCountsGroupByInterval> getUnAvCount() {
            return unAvCount;
        }

        public Integer getOldFalseCount() {
            return oldFalseCount;
        }

        public Integer getInterval() {
            return interval;
        }

        public boolean isCurrentResult() {
            return currentResult;
        }

        public AvailableInf getPrevious() {
            return previous;
        }
    }

    public static class  AvailableStatistics{

        private  Long oldRunningTime;
        private  Long oldStopTime;
        private  Integer oldFalseCount;

        public AvailableStatistics( Long oldRunningTime, Long oldStopTime, Integer oldFalseCount) {
            this.oldRunningTime = oldRunningTime;
            this.oldStopTime = oldStopTime;
            this.oldFalseCount = oldFalseCount;
        }

        public Long getOldRunningTime() {
            return oldRunningTime;
        }

        public Long getOldStopTime() {
            return oldStopTime;
        }

        public Integer getOldFalseCount() {
            return oldFalseCount;
        }

    }

    /**
     * 可用性明细信息
     */
    public static class AvailableInf {

        private Date recordDate;

        private Boolean status;

        private Integer interval;

        /**
         * AvailableInf
         * @param recordDate 记录时间
         * @param status 状态
         * @param interval 轮询时间
         */
        public AvailableInf(Date recordDate, Boolean status, Integer interval) {
            this.recordDate = recordDate;
            this.status = status;
            this.interval = interval;
        }

        public Date getRecordDate() {
            return recordDate;
        }

        public Integer getInterval() {
            return interval;
        }

        public Boolean getStatus() {
            return status;
        }
    }

    public static class AvailableCountsGroupByInterval{

        private int count;

        private int interval;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getInterval() {
            return interval;
        }

        public void setInterval(int interval) {
            this.interval = interval;
        }
    }

}
