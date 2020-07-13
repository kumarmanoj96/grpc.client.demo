package com.manoj.grpc.client.demo.clients;

import java.util.Iterator;

import com.proto.greet.GreetManyTimesRequest;
import com.proto.greet.GreetManyTimesResponse;
import com.proto.greet.GreetMessage;
import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.GreetServiceGrpc.GreetServiceBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetClient {
	private final ManagedChannel channel;
	private final GreetServiceBlockingStub stub;

	public GreetClient(int portNumber) {
		this.channel = ManagedChannelBuilder.forAddress("localhost", portNumber).usePlaintext().build();
		this.stub = GreetServiceGrpc.newBlockingStub(this.channel);
	}

	public void shutdown() {
		this.channel.shutdown();
	}

	public GreetResponse Greet(String firstName, String lastName) {
		// created a protocol buffer for GreetMessage
		GreetMessage greetMessage = GreetMessage.newBuilder().setFirstName(firstName).setLastName(lastName).build();
		// created a protocol buffer for GreetRequest
		GreetRequest greetRequest = GreetRequest.newBuilder().setGreetMessage(greetMessage).build();
		// calling Greet RPC
		return this.stub.greet(greetRequest);
	}

	public Iterator<GreetManyTimesResponse> GreetManyTimes(String firstName, String lastName) {
		// created a protocol buffer for GreetMessage
		GreetMessage greetMessage = GreetMessage.newBuilder().setFirstName(firstName).setLastName(lastName).build();
		// created a protocol buffer for GreetRequest
		GreetManyTimesRequest greetRequest = GreetManyTimesRequest.newBuilder().setGreetMessage(greetMessage).build();
		// calling GreetManyTimes RPC
		return this.stub.greetManyTimes(greetRequest);
	}

}
