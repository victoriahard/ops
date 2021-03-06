
#include <ops.h>
#include "hello/HelloDataSubscriber.h"
#include "HelloWorld/HelloWorldTypeFactory.h"
#include <iostream>
#include <vector>

//Create a class to act as a listener for OPS data 
class Main : ops::DataListener
{
public:
	//Use a member subscriber so we can use it from onNewData, see below.
	hello::HelloDataSubscriber* sub;
	ops::KeyFilterQoSPolicy* keyFilter;

public:

	Main()
	{
		//Create a Participant (i.e. an entry point for using ops.), compare with your ops_config.xml
		ops::Participant* participant = ops::Participant::getInstance("HelloDomain");
		if(!participant)
		{
			std::cout << "Create participant failed. do you have ops_config.xml in your rundirectory?" << std::endl;
			Sleep(10000); exit(1);
		}

		//Add type support for our types, to make this participant understand what we are talking
		participant->addTypeSupport(new HelloWorld::HelloWorldTypeFactory());

		//Now, create the Topic we wish to subscribe on. Might throw ops::NoSuchTopicException if no such Topic exist in ops_config.xml
		ops::Topic topic = participant->createTopic("HelloTopic");

		//Create a subscriber on that topic.
		sub = new hello::HelloDataSubscriber(topic);

		//Add this class as a listener for new data events
		sub->addDataListener(this);

		//Create and add key filter that will make us only receive samples published with key "cpp_sample"
		keyFilter = new ops::KeyFilterQoSPolicy("cpp_sample");
		sub->addFilterQoSPolicy(keyFilter);

		//Start the subscription
		sub->start();

	}
	///Override from ops::DataListener, called whenever new data arrives.
	void onNewData(ops::DataNotifier* subscriber)
	{
		hello::HelloData data;
		if(sub == subscriber)
		{
			sub->getData(&data);
			std::cout << data.helloString << std::endl;
		}
	}
	~Main()
	{
		delete sub;
	}

};

//Application entry point
int main(int argc, char* args)
{
	//Create an object that will listen to OPS events
	Main m;

	//Just keep program alive, action will take place in Main::onNewData()
	while(true)
	{
		Sleep(10000);
	}

	return 0;
}

