/**
 */
package org.yakindu.sct.model.stext.stext.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.yakindu.base.types.Annotation;
import org.yakindu.base.types.Declaration;
import org.yakindu.base.types.MetaComposite;
import org.yakindu.base.types.TypesPackage;
import org.yakindu.base.types.impl.TypeAliasImpl;
import org.yakindu.sct.model.stext.stext.StextPackage;
import org.yakindu.sct.model.stext.stext.TypeAliasDefinition;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type Alias Definition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.yakindu.sct.model.stext.stext.impl.TypeAliasDefinitionImpl#getMetaFeatures <em>Meta Features</em>}</li>
 *   <li>{@link org.yakindu.sct.model.stext.stext.impl.TypeAliasDefinitionImpl#isStatic <em>Static</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TypeAliasDefinitionImpl extends TypeAliasImpl implements TypeAliasDefinition {
	/**
	 * The cached value of the '{@link #getMetaFeatures() <em>Meta Features</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetaFeatures()
	 * @generated
	 * @ordered
	 */
	protected EList<Declaration> metaFeatures;
	/**
	 * The default value of the '{@link #isStatic() <em>Static</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStatic()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STATIC_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isStatic() <em>Static</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStatic()
	 * @generated
	 * @ordered
	 */
	protected boolean static_ = STATIC_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypeAliasDefinitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StextPackage.Literals.TYPE_ALIAS_DEFINITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Declaration> getMetaFeatures() {
		if (metaFeatures == null) {
			metaFeatures = new EObjectContainmentEList<Declaration>(Declaration.class, this, StextPackage.TYPE_ALIAS_DEFINITION__META_FEATURES);
		}
		return metaFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isStatic() {
		return static_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatic(boolean newStatic) {
		boolean oldStatic = static_;
		static_ = newStatic;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StextPackage.TYPE_ALIAS_DEFINITION__STATIC, oldStatic, static_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StextPackage.TYPE_ALIAS_DEFINITION__META_FEATURES:
				return ((InternalEList<?>)getMetaFeatures()).basicRemove(otherEnd, msgs);
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
			case StextPackage.TYPE_ALIAS_DEFINITION__META_FEATURES:
				return getMetaFeatures();
			case StextPackage.TYPE_ALIAS_DEFINITION__STATIC:
				return isStatic();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case StextPackage.TYPE_ALIAS_DEFINITION__META_FEATURES:
				getMetaFeatures().clear();
				getMetaFeatures().addAll((Collection<? extends Declaration>)newValue);
				return;
			case StextPackage.TYPE_ALIAS_DEFINITION__STATIC:
				setStatic((Boolean)newValue);
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
			case StextPackage.TYPE_ALIAS_DEFINITION__META_FEATURES:
				getMetaFeatures().clear();
				return;
			case StextPackage.TYPE_ALIAS_DEFINITION__STATIC:
				setStatic(STATIC_EDEFAULT);
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
			case StextPackage.TYPE_ALIAS_DEFINITION__META_FEATURES:
				return metaFeatures != null && !metaFeatures.isEmpty();
			case StextPackage.TYPE_ALIAS_DEFINITION__STATIC:
				return static_ != STATIC_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == MetaComposite.class) {
			switch (derivedFeatureID) {
				case StextPackage.TYPE_ALIAS_DEFINITION__META_FEATURES: return TypesPackage.META_COMPOSITE__META_FEATURES;
				default: return -1;
			}
		}
		if (baseClass == Declaration.class) {
			switch (derivedFeatureID) {
				case StextPackage.TYPE_ALIAS_DEFINITION__STATIC: return TypesPackage.DECLARATION__STATIC;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == MetaComposite.class) {
			switch (baseFeatureID) {
				case TypesPackage.META_COMPOSITE__META_FEATURES: return StextPackage.TYPE_ALIAS_DEFINITION__META_FEATURES;
				default: return -1;
			}
		}
		if (baseClass == Declaration.class) {
			switch (baseFeatureID) {
				case TypesPackage.DECLARATION__STATIC: return StextPackage.TYPE_ALIAS_DEFINITION__STATIC;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (static: ");
		result.append(static_);
		result.append(')');
		return result.toString();
	}
	
	@Override
	public EList<Annotation> getAnnotations() {
		EList<Annotation> result = new BasicEList<Annotation>();
		result.addAll(super.getAnnotations());
		if(getAnnotationInfo() != null) {
			result.addAll(getAnnotationInfo().getAnnotations());
		}
		return ECollections.unmodifiableEList(result);
	}


} //TypeAliasDefinitionImpl
