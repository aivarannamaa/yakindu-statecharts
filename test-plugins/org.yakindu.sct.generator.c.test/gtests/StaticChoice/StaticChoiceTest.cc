
#include "gtest/gtest.h"
#include "StaticChoice.h"

#include "sc_timer_service.h"

static StaticChoice statechart;


class StaticChoiceTest : public ::testing::Test
{
public:
	/* All operations from the SCTUnit test class. */
	void StaticChoiceTest();
	void setTimer(StaticChoice* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic);
	void unsetTimer(StaticChoice* handle, const sc_eventid evid);
protected:
	sc_unit_timer_service_t timer_service;
	virtual void SetUp();
};

static StaticChoiceTest * tc;


void StaticChoiceTest::SetUp()
{
	staticChoice_init(&statechart);
	sc_timer_service_init(
		&timer_service,
		0,
		(sc_run_cycle_fp) &staticChoice_runCycle,
		false,
		200,
		&statechart
	);
	
	
	tc = this;
}
void StaticChoiceTest::StaticChoiceTest()
{
	staticChoice_enter(&statechart);
	EXPECT_TRUE(staticChoice_isStateActive(&statechart, StaticChoice_main_region_Start));
	sc_timer_service_proceed_cycles(&timer_service, 1);
}

void StaticChoiceTest::setTimer(StaticChoice* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	sc_timer_t timer;
	sc_timer_init(&timer, time_ms, periodic, evid);
	insert_timer(&(tc->timer_service), timer);
}

void StaticChoiceTest::unsetTimer(StaticChoice* handle, const sc_eventid evid){
	delete_task(&(tc->timer_service), find_time_event(&timer_service, evid));
}

TEST_F(StaticChoiceTest, StaticChoiceTest) {
	StaticChoiceTest();
}


