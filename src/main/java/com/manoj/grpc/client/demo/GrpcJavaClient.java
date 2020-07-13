package com.manoj.grpc.client.demo;

import java.util.Iterator;

import com.proto.calculator.CalculatorServiceGrpc;
import com.proto.calculator.CalculatorServiceGrpc.CalculatorServiceBlockingStub;
import com.proto.calculator.SumRequest;
import com.proto.calculator.SumResponse;
import com.proto.greet.GreetManyTimesRequest;
import com.proto.greet.GreetManyTimesResponse;
import com.proto.greet.GreetMessage;
import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.GreetServiceGrpc.GreetServiceBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

//@SpringBootApplication
public class GrpcJavaClient {

	public static void main(String[] args) {
//		SpringApplication.run(GreetingClient.class, args);
		System.out.println("hello i am grpc client");
		ManagedChannel channel1 = ManagedChannelBuilder.forAddress("localhost", 51234).usePlaintext().build();

		// created a greet service stub(client)(blocking - synchornous)
		GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel1);

//		created a protocol buffer for GreetMessage
		GreetMessage greetMessage = GreetMessage.newBuilder().setFirstName("manoj").setLastName("kumar:").build();
		// created a protocol buffer for GreetRequest
		GreetRequest greetRequest = GreetRequest.newBuilder().setGreetMessage(greetMessage).build();
		// called the RPC and got the response (protocol buffers
		GreetResponse greetResponse = greetClient.greet(greetRequest);
		System.out.println(greetResponse.getResult());
//		System.out.println("sutting down channel");
//		channel1.shutdown();

		ManagedChannel channel2 = ManagedChannelBuilder.forAddress("localhost", 11111).usePlaintext().build();
		CalculatorServiceBlockingStub calculatorClient = CalculatorServiceGrpc.newBlockingStub(channel2);

		SumRequest sumRequest = SumRequest.newBuilder().setFirstNumber(3).setSecondNumber(7).build();
		SumResponse sumResponse = calculatorClient.sum(sumRequest);
		System.out.println(sumResponse.getResult());
		channel2.shutdown();

		// server streaming
		GreetManyTimesRequest greetManyTimesRequest = GreetManyTimesRequest.newBuilder().setGreetMessage(greetMessage)
				.build();
		Iterator<GreetManyTimesResponse> greetManyTimesResponse = greetClient.greetManyTimes(greetManyTimesRequest);
		greetManyTimesResponse.forEachRemaining(resp -> {
			System.out.println(resp.getResult());
		});
		System.out.println("sutting down channel");
		channel1.shutdown();
	}

}
