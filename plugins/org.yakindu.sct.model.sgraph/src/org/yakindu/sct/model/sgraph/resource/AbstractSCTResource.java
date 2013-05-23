/**
 * Copyright (c) 2012 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * 	committers of YAKINDU - initial API and implementation
 * 
 */

package org.yakindu.sct.model.sgraph.resource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EClassImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.gmf.runtime.emf.core.resources.GMFResource;
import org.eclipse.xtext.Constants;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.XtextFactory;
import org.eclipse.xtext.diagnostics.DiagnosticMessage;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.linking.ILinker;
import org.eclipse.xtext.linking.ILinkingDiagnosticMessageProvider;
import org.eclipse.xtext.linking.ILinkingDiagnosticMessageProvider.ILinkingDiagnosticContext;
import org.eclipse.xtext.linking.ILinkingService;
import org.eclipse.xtext.linking.impl.LinkingHelper;
import org.eclipse.xtext.linking.impl.XtextLinkingDiagnostic;
import org.eclipse.xtext.linking.lazy.LazyURIEncoder;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.parsetree.reconstr.XtextSerializationException;
import org.eclipse.xtext.resource.XtextSyntaxDiagnostic;
import org.eclipse.xtext.resource.impl.ListBasedDiagnosticConsumer;
import org.eclipse.xtext.scoping.IGlobalScopeProvider;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.Triple;
import org.eclipse.xtext.validation.IConcreteSyntaxValidator;
import org.yakindu.sct.model.sgraph.SGraphPackage;
import org.yakindu.sct.model.sgraph.SpecificationElement;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;

import com.google.common.base.Strings;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * 
 * @author andreas muelder - Initial contribution and API
 * 
 */
public abstract class AbstractSCTResource extends GMFResource {

	public static final String SCT_PREFIX = "SCT_";

	@Inject
	private IParser parser;
	@Inject
	private ILinker linker;
	@Inject
	private ISerializer serializer;
	@Inject
	private LazyURIEncoder encoder;
	@Inject
	private ILinkingService linkingService;
	@Inject
	private ILinkingDiagnosticMessageProvider diagnosticMessageProvider;
	@Inject
	private LinkingHelper linkingHelper;
	@Inject
	private IConcreteSyntaxValidator syntaxValidator;
	@Inject
	private IGlobalScopeProvider scopeProvider;
	@Inject
	private IQualifiedNameConverter nameConverter;
	@Inject
	private IQualifiedNameProvider nameProvider;
	@Inject
	@Named(Constants.LANGUAGE_NAME)
	private String languageName;

	protected boolean isParsing = false;

	protected boolean isLinking = false;

	protected boolean isSerializing = false;

	private boolean serializerEnabled = false;

	protected Multimap<SpecificationElement, Diagnostic> syntaxDiagnostics;

	protected Multimap<SpecificationElement, Diagnostic> linkingDiagnostics;

	protected Map<SpecificationElement, Diagnostic> sublinkDiagnostics;

	protected abstract void parseTransition(Transition element);

	protected abstract void parseState(State element);

	protected abstract void parseStatechart(Statechart element);

	protected abstract void serializeStatechart(Statechart element);

	protected abstract void serializeState(State element);

	protected abstract void serializeTransition(Transition element);

	public AbstractSCTResource(URI uri) {
		super(uri);
		syntaxDiagnostics = HashMultimap.create();
		linkingDiagnostics = HashMultimap.create();
	}

	@Override
	public void attached(EObject eObject) {
		super.attached(eObject);
		if (eObject instanceof SpecificationElement) {
			parseSpecificationElement((SpecificationElement) eObject);
			linkSpecificationElement((SpecificationElement) eObject);
			Adapter parseAdapter = EcoreUtil.getExistingAdapter(eObject,
					ParseAdapter.class);
			if (parseAdapter == null) {
				eObject.eAdapters().add(new ParseAdapter());
			}
			Adapter serializeAdapter = EcoreUtil.getExistingAdapter(eObject,
					SerializeAdapter.class);
			if (serializeAdapter == null) {
				eObject.eAdapters().add(new SerializeAdapter());
			}
		}
	}

	@Override
	public void detached(EObject eObject) {
		if (eObject instanceof SpecificationElement) {
			Adapter parseAdapter = EcoreUtil.getExistingAdapter(eObject,
					ParseAdapter.class);
			if (parseAdapter != null) {
				eObject.eAdapters().remove(parseAdapter);
			}
			Adapter serializeAdapter = EcoreUtil.getExistingAdapter(eObject,
					SerializeAdapter.class);
			if (serializeAdapter != null) {
				eObject.eAdapters().remove(serializeAdapter);
			}
		}
		super.detached(eObject);
	}

	@Override
	public synchronized EObject getEObject(String uriFragment) {
		if (encoder.isCrossLinkFragment(this, uriFragment)) {
			Triple<EObject, EReference, INode> triple = encoder.decode(this,
					uriFragment);
			List<EObject> linkedObjects = null;
			if (triple.getSecond().isContainment()) {
				linkedObjects = resolveLocalXtextFragment(triple.getFirst(),
						triple.getSecond(), triple.getThird());
			} else {
				linkedObjects = linkingService.getLinkedObjects(
						triple.getFirst(), triple.getSecond(),
						triple.getThird());
			}
			if (!linkedObjects.isEmpty()) {
				return linkedObjects.get(0);
			} else {
				createDiagnostic(triple);
			}
		}
		EObject sctObject = getSCTEObject(uriFragment);
		if (sctObject != null) {
			return sctObject;
		}
		return super.getEObject(uriFragment);
	}

	protected EObject getSCTEObject(String uriFragment) {
		if (uriFragment != null && uriFragment.startsWith(SCT_PREFIX)) {
			return getEmfEObject(uriFragment.substring(SCT_PREFIX.length()));
		}
		return null;
	}

	protected EObject getEmfEObject(String uriFragment) {
		int length = uriFragment.length();
		if (length > 0) {
			if (uriFragment.charAt(0) == '/') {
				ArrayList<String> uriFragmentPath = new ArrayList<String>(4);
				int start = 1;
				for (int i = 1; i < length; ++i) {
					if (uriFragment.charAt(i) == '/') {
						uriFragmentPath.add(start == i ? "" : uriFragment
								.substring(start, i));
						start = i + 1;
					}
				}
				uriFragmentPath.add(uriFragment.substring(start));
				return getEObject(uriFragmentPath);
			} else if (uriFragment.charAt(length - 1) == '?') {
				int index = uriFragment.lastIndexOf('?', length - 2);
				if (index > 0) {
					uriFragment = uriFragment.substring(0, index);
				}
			}
		}
		return null;
	}

	private List<EObject> resolveLocalXtextFragment(EObject container,
			EReference reference, INode node) {
		Object value = container.eGet(reference);
		if (reference.isMany()) {
			List<?> list = (List<?>) value;
			List<INode> nodesForFeature = NodeModelUtils.findNodesForFeature(
					container, reference);
			int index = nodesForFeature.indexOf(node);
			if (list.size() > index) {
				return Collections.singletonList((EObject) list.get(index));
			}
		} else if (value instanceof EObject) {
			return Collections.singletonList((EObject) value);
		}
		return Collections.emptyList();
	}

	@Override
	public synchronized String getURIFragment(EObject eObject) {
		if (unloadingContents != null) {
			return super.getURIFragment(eObject);
		}
		ICompositeNode node = NodeModelUtils.findActualNodeFor(eObject);
		if (node != null
				&& NodeModelUtils.getNode(eObject.eContainer()) != null
				&& eObject.eContainingFeature() != null) {
			return getXtextFragment(eObject, node);
		}
		if (node != null) {
			String sctFragment = getSCTFragment(eObject);
			if (sctFragment != null) {
				return sctFragment;
			}
		}
		return super.getURIFragment(eObject);
	}

	protected String getSCTFragment(EObject eObject) {
		String fragment = getEmfFragment(eObject);
		if (!Strings.isNullOrEmpty(fragment)) {
			return SCT_PREFIX + fragment;
		}
		return null;
	}

	/**
	 * Fragment calculation from ResourceImpl
	 * 
	 * @param eObject
	 * @return
	 */
	protected String getEmfFragment(EObject eObject) {
		InternalEObject internalEObject = (InternalEObject) eObject;
		if (internalEObject.eDirectResource() == this
				|| unloadingContents != null
				&& unloadingContents.contains(internalEObject)) {
			return "/" + getURIFragmentRootSegment(eObject);
		} else {
			List<String> uriFragmentPath = new ArrayList<String>();
			boolean isContained = false;
			for (InternalEObject container = internalEObject
					.eInternalContainer(); container != null; container = internalEObject
					.eInternalContainer()) {
				uriFragmentPath.add(container.eURIFragmentSegment(
						internalEObject.eContainingFeature(), internalEObject));
				internalEObject = container;
				if (container.eDirectResource() == this
						|| unloadingContents != null
						&& unloadingContents.contains(container)) {
					isContained = true;
					break;
				}
			}

			if (!isContained) {
				return "/-1";
			}

			StringBuffer result = new StringBuffer("/");
			result.append(getURIFragmentRootSegment(internalEObject));

			for (int i = uriFragmentPath.size() - 1; i >= 0; --i) {
				result.append('/');
				result.append(uriFragmentPath.get(i));
			}
			return result.toString();
		}
	}

	private String getXtextFragment(EObject eObject, ICompositeNode node) {
		return encoder.encode(eObject.eContainer(),
				(EReference) eObject.eContainingFeature(), node);
	}

	protected void createDiagnostic(Triple<EObject, EReference, INode> triple) {
		SpecificationElement specificationElement = EcoreUtil2
				.getContainerOfType(triple.getFirst(),
						SpecificationElement.class);
		DiagnosticMessage message = createDiagnosticMessage(triple);
		Diagnostic diagnostic = new XtextLinkingDiagnostic(triple.getThird(),
				message.getMessage(), message.getIssueCode(),
				message.getIssueData());
		linkingDiagnostics.put(specificationElement, diagnostic);

	}

	protected DiagnosticMessage createDiagnosticMessage(
			Triple<EObject, EReference, INode> triple) {
		ILinkingDiagnosticMessageProvider.ILinkingDiagnosticContext context = createDiagnosticMessageContext(triple);
		DiagnosticMessage message = diagnosticMessageProvider
				.getUnresolvedProxyMessage(context);
		return message;
	}

	protected ILinkingDiagnosticContext createDiagnosticMessageContext(
			Triple<EObject, EReference, INode> triple) {
		return new DiagnosticMessageContext(triple, linkingHelper);
	}

	// copied from xtext LazyLinkingResource
	public void resolveLazyCrossReferences(CancelIndicator monitor) {
		TreeIterator<Object> iterator = EcoreUtil.getAllContents(this, true);
		while (iterator.hasNext()) {
			InternalEObject source = (InternalEObject) iterator.next();
			EStructuralFeature[] eStructuralFeatures = ((EClassImpl.FeatureSubsetSupplier) source
					.eClass().getEAllStructuralFeatures()).crossReferences();
			if (eStructuralFeatures != null) {
				for (EStructuralFeature crossRef : eStructuralFeatures) {
					if (monitor.isCanceled()) {
						return;
					}
					resolveLazyCrossReference(source, crossRef, monitor);
				}
			}
		}
	}

	// copied from xtext LazyLinkingResource
	protected void resolveLazyCrossReference(InternalEObject source,
			EStructuralFeature crossRef, CancelIndicator monitor) {
		if (crossRef.isDerived())
			return;
		if (crossRef.isMany()) {
			@SuppressWarnings("unchecked")
			InternalEList<EObject> list = (InternalEList<EObject>) source
					.eGet(crossRef);
			for (int i = 0; i < list.size(); i++) {
				EObject proxy = list.basicGet(i);
				if (proxy.eIsProxy()) {
					URI proxyURI = ((InternalEObject) proxy).eProxyURI();
					if (getURI().equals(proxyURI.trimFragment())) {
						final String fragment = proxyURI.fragment();
						if (encoder.isCrossLinkFragment(this, fragment)
								&& !monitor.isCanceled()) {
							EObject target = getEObject(fragment);
							if (target != null) {
								try {
									source.eSetDeliver(false);
									list.setUnique(i, target);
								} finally {
									source.eSetDeliver(true);
								}
							}
						}
					}
				}
			}
		} else {
			EObject proxy = (EObject) source.eGet(crossRef, false);
			if (proxy != null && proxy.eIsProxy()) {
				URI proxyURI = ((InternalEObject) proxy).eProxyURI();
				if (getURI().equals(proxyURI.trimFragment())) {
					final String fragment = proxyURI.fragment();
					if (encoder.isCrossLinkFragment(this, fragment)
							&& !monitor.isCanceled()) {
						EObject target = getEObject(fragment);
						if (target != null) {
							try {
								source.eSetDeliver(false);
								source.eSet(crossRef, target);
							} finally {
								source.eSetDeliver(true);
							}
						}
					}
				}
			}
		}
	}

	public void parseSpecificationElement(SpecificationElement element) {
		Assert.isNotNull(element);
		isParsing = true;
		if (element instanceof Transition) {
			parseTransition((Transition) element);
		} else if (element instanceof State) {
			parseState((State) element);
		} else if (element instanceof Statechart) {
			parseStatechart((Statechart) element);
		}
		isParsing = false;
	}

	protected IParseResult parse(SpecificationElement element, String rule) {
		ParserRule parserRule = XtextFactory.eINSTANCE.createParserRule();
		parserRule.setName(rule);
		String specification = element.getSpecification();
		IParseResult result = parser.parse(parserRule, new StringReader(
				specification != null ? specification : ""));
		createDiagnostics(result, element);
		return result;
	}

	protected void createDiagnostics(IParseResult parseResult,
			SpecificationElement semanticTarget) {
		syntaxDiagnostics.get(semanticTarget).clear();
		for (INode error : parseResult.getSyntaxErrors()) {
			syntaxDiagnostics.put(semanticTarget, new XtextSyntaxDiagnostic(
					error));
		}
	}

	public void linkSpecificationElements() {
		TreeIterator<EObject> iter = getAllContents();
		while (iter.hasNext()) {
			EObject currentObject = iter.next();
			if (currentObject instanceof SpecificationElement) {
				linkSpecificationElement((SpecificationElement) currentObject);
			}
		}
	}

	protected void linkSpecificationElement(SpecificationElement element) {
		isLinking = true;
		final ListBasedDiagnosticConsumer consumer = new ListBasedDiagnosticConsumer();
		linker.linkModel(element, consumer);
		linkingDiagnostics.get(element).clear();
		linkingDiagnostics
				.putAll(element, (consumer.getResult(Severity.ERROR)));
		linkingDiagnostics.putAll(element,
				(consumer.getResult(Severity.WARNING)));
		isLinking = false;
	}

	protected void serializeSpecificationElement(SpecificationElement element) {
		isSerializing = true;
		if (getSyntaxDiagnostics().get(element).size() > 0
				|| getLinkingDiagnostics().get(element).size() > 0) {
			return;
		}
		try {
			if (element instanceof Transition) {
				serializeTransition((Transition) element);
			} else if (element instanceof State) {
				serializeState((State) element);
			} else if (element instanceof Statechart) {
				serializeStatechart((Statechart) element);
			}
		} catch (XtextSerializationException ex) {
			// Leave the old specification
		} catch (IConcreteSyntaxValidator.InvalidConcreteSyntaxException ex) {
			// Leave the old specification
		} finally {
			isSerializing = false;
		}
	}

	protected String serialize(EObject object) {
		if (object != null) {
			return serializer.serialize(object);
		}
		return "";
	}

	public Multimap<SpecificationElement, Diagnostic> getSyntaxDiagnostics() {
		return syntaxDiagnostics;
	}

	public Multimap<SpecificationElement, Diagnostic> getLinkingDiagnostics() {
		return linkingDiagnostics;
	}

	public String getLanguageName() {
		return languageName;
	}

	protected final class SerializeAdapter extends EContentAdapter {

		@Override
		public void notifyChanged(Notification msg) {
			super.notifyChanged(msg);
			if (isSerializerEnabled()) {
				if (isLoading() || isParsing || isLinking || isSerializing) {
					return;
				}
				if (msg.getEventType() == Notification.REMOVING_ADAPTER
						|| msg.getEventType() == Notification.RESOLVE) {
					return;
				}
				Object notifier = msg.getNotifier();
				if (notifier instanceof EObject) {
					EObject eObject = (EObject) notifier;
					SpecificationElement container = EcoreUtil2
							.getContainerOfType(eObject,
									SpecificationElement.class);
					if (container != null) {
						serializeSpecificationElement(container);
					}
				}
			}
		}

		@Override
		public boolean isAdapterForType(Object type) {
			return SerializeAdapter.class == type;
		}
	}

	protected final class ParseAdapter extends AdapterImpl {

		@Override
		public void notifyChanged(Notification msg) {
			if (isSerializing) {
				return;
			}
			if (msg.getFeature() == SGraphPackage.Literals.SPECIFICATION_ELEMENT__SPECIFICATION) {
				String newValString = msg.getNewStringValue();
				String oldVString = msg.getOldStringValue();
				if (newValString != null && !newValString.equals(oldVString)) {
					parseSpecificationElement((SpecificationElement) msg
							.getNotifier());
					linkSpecificationElements();
				}
			}
		}

		@Override
		public boolean isAdapterForType(Object type) {
			return ParseAdapter.class == type;
		}
	}

	public boolean isSerializerEnabled() {
		return serializerEnabled;
	}

	public void setSerializerEnabled(boolean serializerEnabled) {
		this.serializerEnabled = serializerEnabled;
	}

	// copied from xtext LazyLinkingResource
	protected static class DiagnosticMessageContext implements
			ILinkingDiagnosticMessageProvider.ILinkingDiagnosticContext {

		private final Triple<EObject, EReference, INode> triple;
		private final LinkingHelper linkingHelper;

		protected DiagnosticMessageContext(
				Triple<EObject, EReference, INode> triple, LinkingHelper helper) {
			this.triple = triple;
			this.linkingHelper = helper;
		}

		public EObject getContext() {
			return triple.getFirst();
		}

		public EReference getReference() {
			return triple.getSecond();
		}

		public String getLinkText() {
			return linkingHelper.getCrossRefNodeAsString(triple.getThird(),
					true);
		}

	}
}
