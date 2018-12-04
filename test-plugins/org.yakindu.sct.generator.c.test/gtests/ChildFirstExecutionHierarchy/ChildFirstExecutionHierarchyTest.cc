
#include "gtest/gtest.h"
#include "ChildFirstExecutionHierarchy.h"

#include "sc_timer_service.h"

static ChildFirstExecutionHierarchy statechart;


class ChildFirstExecutionHierarchyTest : public ::testing::Test
{
public:
	/* All operations from the SCTUnit test class. */
	void childShouldTakeTransition();
	void parentShouldTakeTransition();
	void grandparentShouldTakeTransition();
	void expectLocalReactrionsExecuteWithNoTransition();
	void assertNoLocalReaction();
	void setTimer(ChildFirstExecutionHierarchy* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic);
	void unsetTimer(ChildFirstExecutionHierarchy* handle, const sc_eventid evid);
protected:
	sc_unit_timer_service_t timer_service;
	virtual void SetUp();
};

static ChildFirstExecutionHierarchyTest * tc;

void assertNoLocalReaction();

void ChildFirstExecutionHierarchyTest::SetUp()
{
	childFirstExecutionHierarchy_init(&statechart);
	sc_timer_service_init(
		&timer_service,
		0,
		(sc_run_cycle_fp) &childFirstExecutionHierarchy_runCycle,
		false,
		200,
		&statechart
	);
	
	
	tc = this;
}
void ChildFirstExecutionHierarchyTest::childShouldTakeTransition()
{
	childFirstExecutionHierarchy_enter(&statechart);
	EXPECT_TRUE(childFirstExecutionHierarchy_isStateActive(&statechart, ChildFirstExecutionHierarchy_ChildFirstExecutionHierarchy_r_A_r_AA_r_AAA));
	childFirstExecutionHierarchyIface_raise_e(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(childFirstExecutionHierarchy_isStateActive(&statechart, ChildFirstExecutionHierarchy_ChildFirstExecutionHierarchy_r_B));
	EXPECT_TRUE(!childFirstExecutionHierarchyIface_get_a_reacted(&statechart));
	EXPECT_TRUE(!childFirstExecutionHierarchyIface_get_aa_reacted(&statechart));
	EXPECT_TRUE(childFirstExecutionHierarchyIface_get_aaa_reacted(&statechart));
	assertNoLocalReaction();
}
void ChildFirstExecutionHierarchyTest::parentShouldTakeTransition()
{
	childFirstExecutionHierarchy_enter(&statechart);
	EXPECT_TRUE(childFirstExecutionHierarchy_isStateActive(&statechart, ChildFirstExecutionHierarchy_ChildFirstExecutionHierarchy_r_A_r_AA_r_AAA));
	childFirstExecutionHierarchyIface_set_disable_aaa(&statechart,true);
	childFirstExecutionHierarchyIface_raise_e(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(childFirstExecutionHierarchy_isStateActive(&statechart, ChildFirstExecutionHierarchy_ChildFirstExecutionHierarchy_r_B));
	EXPECT_TRUE(!childFirstExecutionHierarchyIface_get_a_reacted(&statechart));
	EXPECT_TRUE(childFirstExecutionHierarchyIface_get_aa_reacted(&statechart));
	EXPECT_TRUE(!childFirstExecutionHierarchyIface_get_aaa_reacted(&statechart));
	EXPECT_TRUE(!childFirstExecutionHierarchyIface_get_a_local(&statechart));
	EXPECT_TRUE(!childFirstExecutionHierarchyIface_get_aa_local(&statechart));
	EXPECT_TRUE(childFirstExecutionHierarchyIface_get_aaa_local(&statechart));
}
void ChildFirstExecutionHierarchyTest::grandparentShouldTakeTransition()
{
	childFirstExecutionHierarchy_enter(&statechart);
	EXPECT_TRUE(childFirstExecutionHierarchy_isStateActive(&statechart, ChildFirstExecutionHierarchy_ChildFirstExecutionHierarchy_r_A_r_AA_r_AAA));
	childFirstExecutionHierarchyIface_set_disable_aa(&statechart,true);
	childFirstExecutionHierarchyIface_set_disable_aaa(&statechart,true);
	childFirstExecutionHierarchyIface_raise_e(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(childFirstExecutionHierarchy_isStateActive(&statechart, ChildFirstExecutionHierarchy_ChildFirstExecutionHierarchy_r_B));
	EXPECT_TRUE(childFirstExecutionHierarchyIface_get_a_reacted(&statechart));
	EXPECT_TRUE(!childFirstExecutionHierarchyIface_get_aa_reacted(&statechart));
	EXPECT_TRUE(!childFirstExecutionHierarchyIface_get_aaa_reacted(&statechart));
	EXPECT_TRUE(!childFirstExecutionHierarchyIface_get_a_local(&statechart));
	EXPECT_TRUE(childFirstExecutionHierarchyIface_get_aa_local(&statechart));
	EXPECT_TRUE(childFirstExecutionHierarchyIface_get_aaa_local(&statechart));
}
void ChildFirstExecutionHierarchyTest::expectLocalReactrionsExecuteWithNoTransition()
{
	childFirstExecutionHierarchy_enter(&statechart);
	EXPECT_TRUE(childFirstExecutionHierarchy_isStateActive(&statechart, ChildFirstExecutionHierarchy_ChildFirstExecutionHierarchy_r_A_r_AA_r_AAA));
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(childFirstExecutionHierarchy_isStateActive(&statechart, ChildFirstExecutionHierarchy_ChildFirstExecutionHierarchy_r_A_r_AA_r_AAA));
	EXPECT_TRUE(childFirstExecutionHierarchyIface_get_a_local(&statechart));
	EXPECT_TRUE(childFirstExecutionHierarchyIface_get_aa_local(&statechart));
	EXPECT_TRUE(childFirstExecutionHierarchyIface_get_aaa_local(&statechart));
}
void ChildFirstExecutionHierarchyTest::assertNoLocalReaction()
{
	EXPECT_TRUE(!childFirstExecutionHierarchyIface_get_a_local(&statechart));
	EXPECT_TRUE(!childFirstExecutionHierarchyIface_get_aa_local(&statechart));
	EXPECT_TRUE(!childFirstExecutionHierarchyIface_get_aaa_local(&statechart));
}

void ChildFirstExecutionHierarchyTest::setTimer(ChildFirstExecutionHierarchy* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	sc_timer_t timer;
	sc_timer_init(&timer, time_ms, periodic, evid);
	insert_timer(&(tc->timer_service), timer);
}

void ChildFirstExecutionHierarchyTest::unsetTimer(ChildFirstExecutionHierarchy* handle, const sc_eventid evid){
	delete_task(&(tc->timer_service), find_time_event(&timer_service, evid));
}

TEST_F(ChildFirstExecutionHierarchyTest, childShouldTakeTransition) {
	childShouldTakeTransition();
}
TEST_F(ChildFirstExecutionHierarchyTest, parentShouldTakeTransition) {
	parentShouldTakeTransition();
}
TEST_F(ChildFirstExecutionHierarchyTest, grandparentShouldTakeTransition) {
	grandparentShouldTakeTransition();
}
TEST_F(ChildFirstExecutionHierarchyTest, expectLocalReactrionsExecuteWithNoTransition) {
	expectLocalReactrionsExecuteWithNoTransition();
}


