package org.example;

import java.util.ArrayList;
import java.util.List;

public class LA {

	List<Token> tokens = new ArrayList<>();

	DfaState state;

	Token curToken;

	public void analysis(String text) {

		state = DfaState.Initial;
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			switch (state) {
				case Initial -> initToken(c);
				case IntLiteral -> {
					if (Character.isDigit(c)) {
						curToken.text.append(c);
					} else {
						tokens.add(curToken);
						initToken(c);
					}
				}
				case Id -> {
					if (Character.isAlphabetic(c)) {
						curToken.text.append(c);
					} else {
						tokens.add(curToken);
						initToken(c);
					}
				}
				case GT -> {
					if (c == '=') {
						state = DfaState.GE;
						curToken.type = TokenType.GE;
						curToken.text.append(c);
					} else {
						tokens.add(curToken);
						initToken(c);
					}
				}
				case GE -> {
					tokens.add(curToken);
					initToken(c);
				}
			}

		}
		if (curToken != null) {
			tokens.add(curToken);
		}
	}

	public void initToken(char c) {
		StringBuilder tokenText = new StringBuilder();
		TokenType tokenType = null;
		DfaState state = DfaState.Initial;
		this.curToken = null;

		if (Character.isAlphabetic(c)) {
			tokenType = TokenType.Identifier;
			state = DfaState.Id;
			tokenText.append(c);
		} else if (Character.isDigit(c)) {
			tokenType = TokenType.IntLiteral;
			state = DfaState.IntLiteral;
			tokenText.append(c);
		} else if (c == '>') {
			tokenType = TokenType.GT;
			state = DfaState.GT;
			tokenText.append(c);
		}
		if (tokenType == null) {
			this.state = DfaState.Initial;
			return;
		}
		final Token token = new Token();
		token.text = tokenText;
		token.type = tokenType;
		this.state = state;
		this.curToken = token;

	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Token token : tokens) {
			builder.append(String.format("%-10s\t%-10s\n", token.type, token.text));
		}
		return builder.toString();
	}

}
