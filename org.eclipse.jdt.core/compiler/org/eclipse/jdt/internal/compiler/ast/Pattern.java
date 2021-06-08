/*******************************************************************************
 * Copyright (c) 2021 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * This is an implementation of an early-draft specification developed under the Java
 * Community Process (JCP) and is made available for testing and evaluation purposes
 * only. The code is not compatible with any specification of the JCP.
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.internal.compiler.ast;

public abstract class Pattern extends AbstractExPatNode {

	public enum PatternKind {
		ANY_PATTERN,
		GUARDED_PATTERN,
		TYPE_PATTERN,
		NOT_A_PATTERN,
	}

	public int parenthesisSourceStart;
	public int parenthesisSourceEnd;

	public abstract AbstractVariableDeclaration[] getPatternVariables();

	public abstract String getKindName(); // convenience method.

	public abstract PatternKind kind();

	@Override
	public StringBuffer print(int indent, StringBuffer output) {
		printIndent(indent, output);
		return printPattern(indent, output);
	}

	public abstract StringBuffer printPattern(int indent, StringBuffer output);

}