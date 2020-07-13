package com.manoj.grpc.client.demo;

import java.util.Iterator;

import com.manoj.grpc.client.demo.clients.CalculatorClient;
import com.manoj.grpc.client.demo.clients.GreetClient;
import com.proto.calculator.SumResponse;
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
		System.out.println("hello i am grpc client");
		/*
		 * 1.creating channel and then creating stub(client) from that channel and then
		 * calling RPC's
		 */
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 51234).usePlaintext().build();

		// created a greet service stub(client)(blocking - synchornous)
		GreetServiceBlockingStub greetServiceClient = GreetServiceGrpc.newBlockingStub(channel);

		// created a protocol buffer for GreetMessage
		GreetMessage greetMessage = GreetMessage.newBuilder().setFirstName("manoj").setLastName("kumar:").build();
		// created a protocol buffer for GreetRequest
		GreetRequest greetRequest = GreetRequest.newBuilder().setGreetMessage(greetMessage).build();
		// called the RPC and got the response (protocol buffers
		GreetResponse response = greetServiceClient.greet(greetRequest);
		System.out.println(response.getResult());
		// System.out.println("sutting down channel");
		channel.shutdown();

		/*
		 * all the logic of creating channel , creating stub and shutting down channel
		 * is put inside a helper class
		 */

		GreetClient greetClient = new GreetClient(51234);
		GreetResponse greetResponse = greetClient.Greet("manoj", "kumar");
		System.out.println(greetResponse.getResult());

		Iterator<GreetManyTimesResponse> greetManyTimesResponse = greetClient.GreetManyTimes("manoj", "kumar");

		greetManyTimesResponse.forEachRemaining(resp -> {
			System.out.println(resp.getResult());
		});
		greetClient.shutdown();

		CalculatorClient calculatorClient = new CalculatorClient(11111);
		SumResponse sumResponse = calculatorClient.Sum(3, 7);
		System.out.println(sumResponse.getResult());
		calculatorClient.shutdown();

	}

}
