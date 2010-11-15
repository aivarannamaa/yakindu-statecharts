/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipselabs.mscript.language.imperativemodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipselabs.mscript.language.ast.Expression;
import org.eclipselabs.mscript.language.imperativemodel.Compound;
import org.eclipselabs.mscript.language.imperativemodel.ForeachStatement;
import org.eclipselabs.mscript.language.imperativemodel.ImperativeModelPackage;
import org.eclipselabs.mscript.language.imperativemodel.VariableDeclaration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Foreach Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipselabs.mscript.language.imperativemodel.impl.ForeachStatementImpl#getIterationVariableDeclaration <em>Iteration Variable Declaration</em>}</li>
 *   <li>{@link org.eclipselabs.mscript.language.imperativemodel.impl.ForeachStatementImpl#getCollectionExpression <em>Collection Expression</em>}</li>
 *   <li>{@link org.eclipselabs.mscript.language.imperativemodel.impl.ForeachStatementImpl#getBody <em>Body</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ForeachStatementImpl extends StatementImpl implements ForeachStatement {
	/**
	 * The cached value of the '{@link #getIterationVariableDeclaration() <em>Iteration Variable Declaration</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIterationVariableDeclaration()
	 * @generated
	 * @ordered
	 */
	protected VariableDeclaration iterationVariableDeclaration;

	/**
	 * The cached value of the '{@link #getCollectionExpression() <em>Collection Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCollectionExpression()
	 * @generated
	 * @ordered
	 */
	protected Expression collectionExpression;

	/**
	 * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected Compound body;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ForeachStatementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImperativeModelPackage.Literals.FOREACH_STATEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VariableDeclaration getIterationVariableDeclaration() {
		return iterationVariableDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIterationVariableDeclaration(VariableDeclaration newIterationVariableDeclaration, NotificationChain msgs) {
		VariableDeclaration oldIterationVariableDeclaration = iterationVariableDeclaration;
		iterationVariableDeclaration = newIterationVariableDeclaration;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ImperativeModelPackage.FOREACH_STATEMENT__ITERATION_VARIABLE_DECLARATION, oldIterationVariableDeclaration, newIterationVariableDeclaration);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIterationVariableDeclaration(VariableDeclaration newIterationVariableDeclaration) {
		if (newIterationVariableDeclaration != iterationVariableDeclaration) {
			NotificationChain msgs = null;
			if (iterationVariableDeclaration != null)
				msgs = ((InternalEObject)iterationVariableDeclaration).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ImperativeModelPackage.FOREACH_STATEMENT__ITERATION_VARIABLE_DECLARATION, null, msgs);
			if (newIterationVariableDeclaration != null)
				msgs = ((InternalEObject)newIterationVariableDeclaration).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ImperativeModelPackage.FOREACH_STATEMENT__ITERATION_VARIABLE_DECLARATION, null, msgs);
			msgs = basicSetIterationVariableDeclaration(newIterationVariableDeclaration, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImperativeModelPackage.FOREACH_STATEMENT__ITERATION_VARIABLE_DECLARATION, newIterationVariableDeclaration, newIterationVariableDeclaration));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getCollectionExpression() {
		return collectionExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCollectionExpression(Expression newCollectionExpression, NotificationChain msgs) {
		Expression oldCollectionExpression = collectionExpression;
		collectionExpression = newCollectionExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ImperativeModelPackage.FOREACH_STATEMENT__COLLECTION_EXPRESSION, oldCollectionExpression, newCollectionExpression);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCollectionExpression(Expression newCollectionExpression) {
		if (newCollectionExpression != collectionExpression) {
			NotificationChain msgs = null;
			if (collectionExpression != null)
				msgs = ((InternalEObject)collectionExpression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ImperativeModelPackage.FOREACH_STATEMENT__COLLECTION_EXPRESSION, null, msgs);
			if (newCollectionExpression != null)
				msgs = ((InternalEObject)newCollectionExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ImperativeModelPackage.FOREACH_STATEMENT__COLLECTION_EXPRESSION, null, msgs);
			msgs = basicSetCollectionExpression(newCollectionExpression, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImperativeModelPackage.FOREACH_STATEMENT__COLLECTION_EXPRESSION, newCollectionExpression, newCollectionExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Compound getBody() {
		return body;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBody(Compound newBody, NotificationChain msgs) {
		Compound oldBody = body;
		body = newBody;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ImperativeModelPackage.FOREACH_STATEMENT__BODY, oldBody, newBody);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBody(Compound newBody) {
		if (newBody != body) {
			NotificationChain msgs = null;
			if (body != null)
				msgs = ((InternalEObject)body).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ImperativeModelPackage.FOREACH_STATEMENT__BODY, null, msgs);
			if (newBody != null)
				msgs = ((InternalEObject)newBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ImperativeModelPackage.FOREACH_STATEMENT__BODY, null, msgs);
			msgs = basicSetBody(newBody, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImperativeModelPackage.FOREACH_STATEMENT__BODY, newBody, newBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ImperativeModelPackage.FOREACH_STATEMENT__ITERATION_VARIABLE_DECLARATION:
				return basicSetIterationVariableDeclaration(null, msgs);
			case ImperativeModelPackage.FOREACH_STATEMENT__COLLECTION_EXPRESSION:
				return basicSetCollectionExpression(null, msgs);
			case ImperativeModelPackage.FOREACH_STATEMENT__BODY:
				return basicSetBody(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ImperativeModelPackage.FOREACH_STATEMENT__ITERATION_VARIABLE_DECLARATION:
				return getIterationVariableDeclaration();
			case ImperativeModelPackage.FOREACH_STATEMENT__COLLECTION_EXPRESSION:
				return getCollectionExpression();
			case ImperativeModelPackage.FOREACH_STATEMENT__BODY:
				return getBody();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ImperativeModelPackage.FOREACH_STATEMENT__ITERATION_VARIABLE_DECLARATION:
				setIterationVariableDeclaration((VariableDeclaration)newValue);
				return;
			case ImperativeModelPackage.FOREACH_STATEMENT__COLLECTION_EXPRESSION:
				setCollectionExpression((Expression)newValue);
				return;
			case ImperativeModelPackage.FOREACH_STATEMENT__BODY:
				setBody((Compound)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ImperativeModelPackage.FOREACH_STATEMENT__ITERATION_VARIABLE_DECLARATION:
				setIterationVariableDeclaration((VariableDeclaration)null);
				return;
			case ImperativeModelPackage.FOREACH_STATEMENT__COLLECTION_EXPRESSION:
				setCollectionExpression((Expression)null);
				return;
			case ImperativeModelPackage.FOREACH_STATEMENT__BODY:
				setBody((Compound)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ImperativeModelPackage.FOREACH_STATEMENT__ITERATION_VARIABLE_DECLARATION:
				return iterationVariableDeclaration != null;
			case ImperativeModelPackage.FOREACH_STATEMENT__COLLECTION_EXPRESSION:
				return collectionExpression != null;
			case ImperativeModelPackage.FOREACH_STATEMENT__BODY:
				return body != null;
		}
		return super.eIsSet(featureID);
	}

} //ForeachStatementImpl
