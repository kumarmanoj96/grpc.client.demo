package com.manoj.grpc.client.demo.clients;

import com.proto.calculator.CalculatorServiceGrpc;
import com.proto.calculator.CalculatorServiceGrpc.CalculatorServiceBlockingStub;
import com.proto.calculator.SumRequest;
import com.proto.calculator.SumResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CalculatorClient {

	private final ManagedChannel channel;
	private final CalculatorServiceBlockingStub stub;

	public CalculatorClient(int portNumber) {
		this.channel = ManagedChannelBuilder.forAddress("localhost", portNumber).usePlaintext().build();
		this.stub = CalculatorServiceGrpc.newBlockingStub(this.channel);
	}

	public void shutdown() {
		this.channel.shutdown();
	}

	public SumResponse Sum(int firstNumber, int secondNumber) {
		SumRequest request = SumRequest.newBuilder().setFirstNumber(firstNumber).setSecondNumber(secondNumber).build();
		// calling sum RPC
		return this.stub.sum(request);
	}

}
