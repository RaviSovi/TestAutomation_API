//package com.project.apiautomation.utils;
//
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;
//import io.cucumber.plugin.ConcurrentEventListener;
//import io.cucumber.plugin.event.EventPublisher;
//import io.cucumber.plugin.event.Result;
//import io.cucumber.plugin.event.Status;
//import io.cucumber.plugin.event.TestCase;
//import io.cucumber.plugin.event.TestCaseFinished;
//
//public class TestListener implements ConcurrentEventListener {
//	private static final Logger LOG = LogManager.getLogger(TestListener.class);
//
//	@Override
//	public void setEventPublisher(EventPublisher publisher) {
//		publisher.registerHandlerFor(TestCaseFinished.class, this::handleTestCaseFinished);
//	}
//
//	private void handleTestCaseFinished(TestCaseFinished event) {
//		TestCase testCase = event.getTestCase();
//		Result result = event.getResult();
//		Status status = result.getStatus();
//		Throwable error = result.getError();
//		String scenarioName = testCase.getName();
//		if (error != null) {
//			LOG.info(error);
//		}
//		ER.Info("*****************************************************************************************");
//		ER.Info("	Scenario: " + scenarioName + " --> " + status.name());
//		ER.Info("*****************************************************************************************");
//	}
//}