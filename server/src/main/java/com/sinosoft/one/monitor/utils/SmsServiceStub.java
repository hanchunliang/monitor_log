package com.sinosoft.one.monitor.utils;


/*
 *  SmsServiceStub java implementation
 */
public class SmsServiceStub /*extends Stub*/ {/*
	protected org.apache.axis2.description.AxisOperation[] _operations;

	// hashmaps to keep the fault mapping
	
	private Map faultExceptionNameMap = new HashMap();
	private Map faultExceptionClassNameMap = new HashMap();
	private Map faultMessageMap = new HashMap();

	private static int counter = 0;

	private static synchronized java.lang.String getUniqueSuffix() {
		// reset the counter if it is greater than 99999
		if (counter > 99999) {
			counter = 0;
		}
		counter = counter + 1;
		return java.lang.Long.toString(java.lang.System.currentTimeMillis())
				+ "_" + counter;
	}

	private void populateAxisService() throws org.apache.axis2.AxisFault {

		// creating the Service with a unique name
		_service = new org.apache.axis2.description.AxisService("SmsService"
				+ getUniqueSuffix());
		addAnonymousOperations();

		// creating the operations
		org.apache.axis2.description.AxisOperation __operation;

		_operations = new org.apache.axis2.description.AxisOperation[2];

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://http.net.sms.wisdom.com", "sendSMS"));
		_service.addOperation(__operation);

		_operations[0] = __operation;

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"http://http.net.sms.wisdom.com", "createActivitySpreadTask"));
		_service.addOperation(__operation);

		_operations[1] = __operation;

	}

	// populates the faults
	private void populateFaults() {

	}

	*//**
	 * Constructor that takes in a configContext
	 *//*

	public SmsServiceStub(
			org.apache.axis2.context.ConfigurationContext configurationContext,
			java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
		this(configurationContext, targetEndpoint, false);
	}

	*//**
	 * Constructor that takes in a configContext and useseperate listner
	 *//*
	public SmsServiceStub(
			org.apache.axis2.context.ConfigurationContext configurationContext,
			java.lang.String targetEndpoint, boolean useSeparateListener)
			throws org.apache.axis2.AxisFault {
		// To populate AxisService
		populateAxisService();
		populateFaults();

		_serviceClient = new org.apache.axis2.client.ServiceClient(
				configurationContext, _service);

		_serviceClient.getOptions().setTo(
				new org.apache.axis2.addressing.EndpointReference(
						targetEndpoint));
		_serviceClient.getOptions().setUseSeparateListener(useSeparateListener);

		// Set the soap version
		_serviceClient
				.getOptions()
				.setSoapVersionURI(
						org.apache.axiom.soap.SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);

	}

	*//**
	 * Default Constructor
	 *//*
	public SmsServiceStub(
			org.apache.axis2.context.ConfigurationContext configurationContext)
			throws org.apache.axis2.AxisFault {

		this(
				configurationContext,
				"http://localhost:7001/wssmsif2/services/SmsService.SmsServiceHttpSoap12Endpoint/");

	}

	*//**
	 * Default Constructor
	 *//*
	public SmsServiceStub() throws org.apache.axis2.AxisFault {

		this(
				"http://localhost:7001/wssmsif2/services/SmsService.SmsServiceHttpSoap12Endpoint/");

	}

	*//**
	 * Constructor taking the target endpoint
	 *//*
	public SmsServiceStub(java.lang.String targetEndpoint)
			throws org.apache.axis2.AxisFault {
		this(null, targetEndpoint);
	}

	*//**
	 * Auto generated method signature
	 * 
	 * @see SmsService#sendSMS
	 * @param sendSMS0
	 *//*

	public SmsServiceStub.SendSMSResponse sendSMS(

	SmsServiceStub.SendSMS sendSMS0)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[0].getName());
			_operationClient.getOptions().setAction("urn:sendSMS");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), sendSMS0,
					optimizeContent(new javax.xml.namespace.QName(
							"http://http.net.sms.wisdom.com", "sendSMS")),
					new javax.xml.namespace.QName(
							"http://http.net.sms.wisdom.com", "sendSMS"));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(), SmsServiceStub.SendSMSResponse.class,
					getEnvelopeNamespaces(_returnEnv));

			return (SmsServiceStub.SendSMSResponse) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap
						.containsKey(new org.apache.axis2.client.FaultMapKey(
								faultElt.getQName(), "sendSMS"))) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(new org.apache.axis2.client.FaultMapKey(
										faultElt.getQName(), "sendSMS"));
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(new org.apache.axis2.client.FaultMapKey(
										faultElt.getQName(), "sendSMS"));
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			if (_messageContext.getTransportOut() != null) {
				_messageContext.getTransportOut().getSender()
						.cleanup(_messageContext);
			}
		}
	}

	*//**
	 * Auto generated method signature for Asynchronous Invocations
	 * 
	 * @see SmsService#startsendSMS
	 * @param sendSMS0
	 *//*
	public void startsendSMS(

	SmsServiceStub.SendSMS sendSMS0,

	final SmsServiceCallbackHandler callback)

	throws java.rmi.RemoteException {

		org.apache.axis2.client.OperationClient _operationClient = _serviceClient
				.createClient(_operations[0].getName());
		_operationClient.getOptions().setAction("urn:sendSMS");
		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

		addPropertyToOperationClient(
				_operationClient,
				org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
				"&");

		// create SOAP envelope with that payload
		org.apache.axiom.soap.SOAPEnvelope env = null;
		final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

		// Style is Doc.

		env = toEnvelope(getFactory(_operationClient.getOptions()
				.getSoapVersionURI()), sendSMS0,
				optimizeContent(new javax.xml.namespace.QName(
						"http://http.net.sms.wisdom.com", "sendSMS")),
				new javax.xml.namespace.QName("http://http.net.sms.wisdom.com",
						"sendSMS"));

		// adding SOAP soap_headers
		_serviceClient.addHeadersToEnvelope(env);
		// create message context with that soap envelope
		_messageContext.setEnvelope(env);

		// add the message context to the operation client
		_operationClient.addMessageContext(_messageContext);

		_operationClient
				.setCallback(new org.apache.axis2.client.async.AxisCallback() {
					public void onMessage(
							org.apache.axis2.context.MessageContext resultContext) {
						try {
							org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext
									.getEnvelope();

							java.lang.Object object = fromOM(resultEnv
									.getBody().getFirstElement(),
									SmsServiceStub.SendSMSResponse.class,
									getEnvelopeNamespaces(resultEnv));
							callback.receiveResultsendSMS((SmsServiceStub.SendSMSResponse) object);

						} catch (org.apache.axis2.AxisFault e) {
							callback.receiveErrorsendSMS(e);
						}
					}

					public void onError(java.lang.Exception error) {
						if (error instanceof org.apache.axis2.AxisFault) {
							org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
							org.apache.axiom.om.OMElement faultElt = f
									.getDetail();
							if (faultElt != null) {
								if (faultExceptionNameMap
										.containsKey(new org.apache.axis2.client.FaultMapKey(
												faultElt.getQName(), "sendSMS"))) {
									// make the fault by reflection
									try {
										java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
												.get(new org.apache.axis2.client.FaultMapKey(
														faultElt.getQName(),
														"sendSMS"));
										java.lang.Class exceptionClass = java.lang.Class
												.forName(exceptionClassName);
										java.lang.Exception ex = (java.lang.Exception) exceptionClass
												.newInstance();
										// message class
										java.lang.String messageClassName = (java.lang.String) faultMessageMap
												.get(new org.apache.axis2.client.FaultMapKey(
														faultElt.getQName(),
														"sendSMS"));
										java.lang.Class messageClass = java.lang.Class
												.forName(messageClassName);
										java.lang.Object messageObject = fromOM(
												faultElt, messageClass, null);
										java.lang.reflect.Method m = exceptionClass
												.getMethod(
														"setFaultMessage",
														new java.lang.Class[] { messageClass });
										m.invoke(
												ex,
												new java.lang.Object[] { messageObject });

										callback.receiveErrorsendSMS(new java.rmi.RemoteException(
												ex.getMessage(), ex));
									} catch (java.lang.ClassCastException e) {
										// we cannot intantiate the class -
										// throw the original Axis fault
										callback.receiveErrorsendSMS(f);
									} catch (java.lang.ClassNotFoundException e) {
										// we cannot intantiate the class -
										// throw the original Axis fault
										callback.receiveErrorsendSMS(f);
									} catch (java.lang.NoSuchMethodException e) {
										// we cannot intantiate the class -
										// throw the original Axis fault
										callback.receiveErrorsendSMS(f);
									} catch (java.lang.reflect.InvocationTargetException e) {
										// we cannot intantiate the class -
										// throw the original Axis fault
										callback.receiveErrorsendSMS(f);
									} catch (java.lang.IllegalAccessException e) {
										// we cannot intantiate the class -
										// throw the original Axis fault
										callback.receiveErrorsendSMS(f);
									} catch (java.lang.InstantiationException e) {
										// we cannot intantiate the class -
										// throw the original Axis fault
										callback.receiveErrorsendSMS(f);
									} catch (org.apache.axis2.AxisFault e) {
										// we cannot intantiate the class -
										// throw the original Axis fault
										callback.receiveErrorsendSMS(f);
									}
								} else {
									callback.receiveErrorsendSMS(f);
								}
							} else {
								callback.receiveErrorsendSMS(f);
							}
						} else {
							callback.receiveErrorsendSMS(error);
						}
					}

					public void onFault(
							org.apache.axis2.context.MessageContext faultContext) {
						org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils
								.getInboundFaultFromMessageContext(faultContext);
						onError(fault);
					}

					public void onComplete() {
						try {
							_messageContext.getTransportOut().getSender()
									.cleanup(_messageContext);
						} catch (org.apache.axis2.AxisFault axisFault) {
							callback.receiveErrorsendSMS(axisFault);
						}
					}
				});

		org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
		if (_operations[0].getMessageReceiver() == null
				&& _operationClient.getOptions().isUseSeparateListener()) {
			_callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
			_operations[0].setMessageReceiver(_callbackReceiver);
		}

		// execute the operation client
		_operationClient.execute(false);

	}

	*//**
	 * Auto generated method signature
	 * 
	 * @see SmsService#createActivitySpreadTask
	 * @param createActivitySpreadTask2
	 *//*

	public SmsServiceStub.CreateActivitySpreadTaskResponse createActivitySpreadTask(

	SmsServiceStub.CreateActivitySpreadTask createActivitySpreadTask2)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[1].getName());
			_operationClient.getOptions().setAction(
					"urn:createActivitySpreadTask");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), createActivitySpreadTask2,
					optimizeContent(new javax.xml.namespace.QName(
							"http://http.net.sms.wisdom.com",
							"createActivitySpreadTask")),
					new javax.xml.namespace.QName(
							"http://http.net.sms.wisdom.com",
							"createActivitySpreadTask"));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(_returnEnv.getBody()
					.getFirstElement(),
					SmsServiceStub.CreateActivitySpreadTaskResponse.class,
					getEnvelopeNamespaces(_returnEnv));

			return (SmsServiceStub.CreateActivitySpreadTaskResponse) object;

		} catch (org.apache.axis2.AxisFault f) {

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt != null) {
				if (faultExceptionNameMap
						.containsKey(new org.apache.axis2.client.FaultMapKey(
								faultElt.getQName(), "createActivitySpreadTask"))) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(new org.apache.axis2.client.FaultMapKey(
										faultElt.getQName(),
										"createActivitySpreadTask"));
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.Exception ex = (java.lang.Exception) exceptionClass
								.newInstance();
						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(new org.apache.axis2.client.FaultMapKey(
										faultElt.getQName(),
										"createActivitySpreadTask"));
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass, null);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			if (_messageContext.getTransportOut() != null) {
				_messageContext.getTransportOut().getSender()
						.cleanup(_messageContext);
			}
		}
	}

	*//**
	 * Auto generated method signature for Asynchronous Invocations
	 * 
	 * @see SmsService#startcreateActivitySpreadTask
	 * @param createActivitySpreadTask2
	 *//*
	public void startcreateActivitySpreadTask(

	SmsServiceStub.CreateActivitySpreadTask createActivitySpreadTask2,

	final SmsServiceCallbackHandler callback)

	throws java.rmi.RemoteException {

		org.apache.axis2.client.OperationClient _operationClient = _serviceClient
				.createClient(_operations[1].getName());
		_operationClient.getOptions().setAction("urn:createActivitySpreadTask");
		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

		addPropertyToOperationClient(
				_operationClient,
				org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
				"&");

		// create SOAP envelope with that payload
		org.apache.axiom.soap.SOAPEnvelope env = null;
		final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

		// Style is Doc.

		env = toEnvelope(getFactory(_operationClient.getOptions()
				.getSoapVersionURI()), createActivitySpreadTask2,
				optimizeContent(new javax.xml.namespace.QName(
						"http://http.net.sms.wisdom.com",
						"createActivitySpreadTask")),
				new javax.xml.namespace.QName("http://http.net.sms.wisdom.com",
						"createActivitySpreadTask"));

		// adding SOAP soap_headers
		_serviceClient.addHeadersToEnvelope(env);
		// create message context with that soap envelope
		_messageContext.setEnvelope(env);

		// add the message context to the operation client
		_operationClient.addMessageContext(_messageContext);

		_operationClient
				.setCallback(new org.apache.axis2.client.async.AxisCallback() {
					public void onMessage(
							org.apache.axis2.context.MessageContext resultContext) {
						try {
							org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext
									.getEnvelope();

							java.lang.Object object = fromOM(
									resultEnv.getBody().getFirstElement(),
									SmsServiceStub.CreateActivitySpreadTaskResponse.class,
									getEnvelopeNamespaces(resultEnv));
							callback.receiveResultcreateActivitySpreadTask((SmsServiceStub.CreateActivitySpreadTaskResponse) object);

						} catch (org.apache.axis2.AxisFault e) {
							callback.receiveErrorcreateActivitySpreadTask(e);
						}
					}

					public void onError(java.lang.Exception error) {
						if (error instanceof org.apache.axis2.AxisFault) {
							org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
							org.apache.axiom.om.OMElement faultElt = f
									.getDetail();
							if (faultElt != null) {
								if (faultExceptionNameMap
										.containsKey(new org.apache.axis2.client.FaultMapKey(
												faultElt.getQName(),
												"createActivitySpreadTask"))) {
									// make the fault by reflection
									try {
										java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
												.get(new org.apache.axis2.client.FaultMapKey(
														faultElt.getQName(),
														"createActivitySpreadTask"));
										java.lang.Class exceptionClass = java.lang.Class
												.forName(exceptionClassName);
										java.lang.Exception ex = (java.lang.Exception) exceptionClass
												.newInstance();
										// message class
										java.lang.String messageClassName = (java.lang.String) faultMessageMap
												.get(new org.apache.axis2.client.FaultMapKey(
														faultElt.getQName(),
														"createActivitySpreadTask"));
										java.lang.Class messageClass = java.lang.Class
												.forName(messageClassName);
										java.lang.Object messageObject = fromOM(
												faultElt, messageClass, null);
										java.lang.reflect.Method m = exceptionClass
												.getMethod(
														"setFaultMessage",
														new java.lang.Class[] { messageClass });
										m.invoke(
												ex,
												new java.lang.Object[] { messageObject });

										callback.receiveErrorcreateActivitySpreadTask(new java.rmi.RemoteException(
												ex.getMessage(), ex));
									} catch (java.lang.ClassCastException e) {
										// we cannot intantiate the class -
										// throw the original Axis fault
										callback.receiveErrorcreateActivitySpreadTask(f);
									} catch (java.lang.ClassNotFoundException e) {
										// we cannot intantiate the class -
										// throw the original Axis fault
										callback.receiveErrorcreateActivitySpreadTask(f);
									} catch (java.lang.NoSuchMethodException e) {
										// we cannot intantiate the class -
										// throw the original Axis fault
										callback.receiveErrorcreateActivitySpreadTask(f);
									} catch (java.lang.reflect.InvocationTargetException e) {
										// we cannot intantiate the class -
										// throw the original Axis fault
										callback.receiveErrorcreateActivitySpreadTask(f);
									} catch (java.lang.IllegalAccessException e) {
										// we cannot intantiate the class -
										// throw the original Axis fault
										callback.receiveErrorcreateActivitySpreadTask(f);
									} catch (java.lang.InstantiationException e) {
										// we cannot intantiate the class -
										// throw the original Axis fault
										callback.receiveErrorcreateActivitySpreadTask(f);
									} catch (org.apache.axis2.AxisFault e) {
										// we cannot intantiate the class -
										// throw the original Axis fault
										callback.receiveErrorcreateActivitySpreadTask(f);
									}
								} else {
									callback.receiveErrorcreateActivitySpreadTask(f);
								}
							} else {
								callback.receiveErrorcreateActivitySpreadTask(f);
							}
						} else {
							callback.receiveErrorcreateActivitySpreadTask(error);
						}
					}

					public void onFault(
							org.apache.axis2.context.MessageContext faultContext) {
						org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils
								.getInboundFaultFromMessageContext(faultContext);
						onError(fault);
					}

					public void onComplete() {
						try {
							_messageContext.getTransportOut().getSender()
									.cleanup(_messageContext);
						} catch (org.apache.axis2.AxisFault axisFault) {
							callback.receiveErrorcreateActivitySpreadTask(axisFault);
						}
					}
				});

		org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
		if (_operations[1].getMessageReceiver() == null
				&& _operationClient.getOptions().isUseSeparateListener()) {
			_callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
			_operations[1].setMessageReceiver(_callbackReceiver);
		}

		// execute the operation client
		_operationClient.execute(false);

	}

	*//**
	 * A utility method that copies the namepaces from the SOAPEnvelope
	 *//*
	private java.util.Map getEnvelopeNamespaces(
			org.apache.axiom.soap.SOAPEnvelope env) {
		java.util.Map returnMap = new java.util.HashMap();
		java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
		while (namespaceIterator.hasNext()) {
			org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator
					.next();
			returnMap.put(ns.getPrefix(), ns.getNamespaceURI());
		}
		return returnMap;
	}

	private javax.xml.namespace.QName[] opNameArray = null;

	private boolean optimizeContent(javax.xml.namespace.QName opName) {

		if (opNameArray == null) {
			return false;
		}
		for (int i = 0; i < opNameArray.length; i++) {
			if (opName.equals(opNameArray[i])) {
				return true;
			}
		}
		return false;
	}

	// http://localhost:7001/wssmsif2/services/SmsService.SmsServiceHttpSoap12Endpoint/
	public static class SendSMSResponse implements
			org.apache.axis2.databinding.ADBBean {

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://http.net.sms.wisdom.com", "sendSMSResponse", "ns3");

		*//**
		 * field for _return
		 *//*

		protected Response local_return;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean local_returnTracker = false;

		public boolean is_returnSpecified() {
			return local_returnTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return Response
		 *//*
		public Response get_return() {
			return local_return;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            _return
		 *//*
		public void set_return(Response param) {
			local_returnTracker = true;

			this.local_return = param;

		}

		*//**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 *//*
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {

			org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
					this, MY_QNAME);
			return factory.createOMElement(dataSource, MY_QNAME);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {

			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"http://http.net.sms.wisdom.com");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":sendSMSResponse",
							xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "sendSMSResponse", xmlWriter);
				}

			}
			if (local_returnTracker) {
				if (local_return == null) {

					writeStartElement(null, "http://http.net.sms.wisdom.com",
							"return", xmlWriter);

					// write the nil attribute
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);
					xmlWriter.writeEndElement();
				} else {
					local_return.serialize(new javax.xml.namespace.QName(
							"http://http.net.sms.wisdom.com", "return"),
							xmlWriter);
				}
			}
			xmlWriter.writeEndElement();

		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("http://http.net.sms.wisdom.com")) {
				return "ns3";
			}
			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		*//**
		 * Utility method to write an element start tag.
		 *//*
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		*//**
		 * Util method to write an attribute with the ns prefix
		 *//*
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			java.lang.String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		*//**
		 * method to handle Qnames
		 *//*

		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}

			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		*//**
		 * Register a namespace prefix
		 *//*
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		*//**
		 * databinding method to get an XML representation of this object
		 * 
		 *//*
		public javax.xml.stream.XMLStreamReader getPullParser(
				javax.xml.namespace.QName qName)
				throws org.apache.axis2.databinding.ADBException {

			java.util.ArrayList elementList = new java.util.ArrayList();
			java.util.ArrayList attribList = new java.util.ArrayList();

			if (local_returnTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://http.net.sms.wisdom.com", "return"));

				elementList.add(local_return == null ? null : local_return);
			}

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(
					qName, elementList.toArray(), attribList.toArray());

		}

		*//**
		 * Factory class that keeps the parse method
		 *//*
		public static class Factory {

			*//**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 *//*
			public static SendSMSResponse parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				SendSMSResponse object = new SendSMSResponse();

				int event;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");
						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"sendSMSResponse".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);
								return (SendSMSResponse) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://http.net.sms.wisdom.com", "return")
									.equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if ("true".equals(nillableValue)
								|| "1".equals(nillableValue)) {
							object.set_return(null);
							reader.next();

							reader.next();

						} else {

							object.set_return(Response.Factory.parse(reader));

							reader.next();
						}
					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());

				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class CreateActivitySpreadTask implements
			org.apache.axis2.databinding.ADBBean {

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://http.net.sms.wisdom.com", "createActivitySpreadTask",
				"ns3");

		*//**
		 * field for UserName
		 *//*

		protected java.lang.String localUserName;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localUserNameTracker = false;

		public boolean isUserNameSpecified() {
			return localUserNameTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getUserName() {
			return localUserName;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            UserName
		 *//*
		public void setUserName(java.lang.String param) {
			localUserNameTracker = true;

			this.localUserName = param;

		}

		*//**
		 * field for Password
		 *//*

		protected java.lang.String localPassword;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localPasswordTracker = false;

		public boolean isPasswordSpecified() {
			return localPasswordTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getPassword() {
			return localPassword;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Password
		 *//*
		public void setPassword(java.lang.String param) {
			localPasswordTracker = true;

			this.localPassword = param;

		}

		*//**
		 * field for SpreakTask
		 *//*

		protected SpreadTask localSpreakTask;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localSpreakTaskTracker = false;

		public boolean isSpreakTaskSpecified() {
			return localSpreakTaskTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return SpreadTask
		 *//*
		public SpreadTask getSpreakTask() {
			return localSpreakTask;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            SpreakTask
		 *//*
		public void setSpreakTask(SpreadTask param) {
			localSpreakTaskTracker = true;

			this.localSpreakTask = param;

		}

		*//**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 *//*
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {

			org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
					this, MY_QNAME);
			return factory.createOMElement(dataSource, MY_QNAME);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {

			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"http://http.net.sms.wisdom.com");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix
									+ ":createActivitySpreadTask", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "createActivitySpreadTask", xmlWriter);
				}

			}
			if (localUserNameTracker) {
				namespace = "http://http.net.sms.wisdom.com";
				writeStartElement(null, namespace, "UserName", xmlWriter);

				if (localUserName == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localUserName);

				}

				xmlWriter.writeEndElement();
			}
			if (localPasswordTracker) {
				namespace = "http://http.net.sms.wisdom.com";
				writeStartElement(null, namespace, "Password", xmlWriter);

				if (localPassword == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localPassword);

				}

				xmlWriter.writeEndElement();
			}
			if (localSpreakTaskTracker) {
				if (localSpreakTask == null) {

					writeStartElement(null, "http://http.net.sms.wisdom.com",
							"spreakTask", xmlWriter);

					// write the nil attribute
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);
					xmlWriter.writeEndElement();
				} else {
					localSpreakTask.serialize(new javax.xml.namespace.QName(
							"http://http.net.sms.wisdom.com", "spreakTask"),
							xmlWriter);
				}
			}
			xmlWriter.writeEndElement();

		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("http://http.net.sms.wisdom.com")) {
				return "ns3";
			}
			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		*//**
		 * Utility method to write an element start tag.
		 *//*
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		*//**
		 * Util method to write an attribute with the ns prefix
		 *//*
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			java.lang.String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		*//**
		 * method to handle Qnames
		 *//*

		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}

			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		*//**
		 * Register a namespace prefix
		 *//*
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		*//**
		 * databinding method to get an XML representation of this object
		 * 
		 *//*
		public javax.xml.stream.XMLStreamReader getPullParser(
				javax.xml.namespace.QName qName)
				throws org.apache.axis2.databinding.ADBException {

			java.util.ArrayList elementList = new java.util.ArrayList();
			java.util.ArrayList attribList = new java.util.ArrayList();

			if (localUserNameTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://http.net.sms.wisdom.com", "UserName"));

				elementList.add(localUserName == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localUserName));
			}
			if (localPasswordTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://http.net.sms.wisdom.com", "Password"));

				elementList.add(localPassword == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localPassword));
			}
			if (localSpreakTaskTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://http.net.sms.wisdom.com", "spreakTask"));

				elementList.add(localSpreakTask == null ? null
						: localSpreakTask);
			}

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(
					qName, elementList.toArray(), attribList.toArray());

		}

		*//**
		 * Factory class that keeps the parse method
		 *//*
		public static class Factory {

			*//**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 *//*
			public static CreateActivitySpreadTask parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				CreateActivitySpreadTask object = new CreateActivitySpreadTask();

				int event;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");
						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"createActivitySpreadTask".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);
								return (CreateActivitySpreadTask) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://http.net.sms.wisdom.com",
									"UserName").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setUserName(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://http.net.sms.wisdom.com",
									"Password").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setPassword(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://http.net.sms.wisdom.com",
									"spreakTask").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if ("true".equals(nillableValue)
								|| "1".equals(nillableValue)) {
							object.setSpreakTask(null);
							reader.next();

							reader.next();

						} else {

							object.setSpreakTask(SpreadTask.Factory
									.parse(reader));

							reader.next();
						}
					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());

				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class TXInsuranceResponse extends TXInsurance implements
			org.apache.axis2.databinding.ADBBean {
		
		 * This type was generated from the piece of schema that had name =
		 * TXInsuranceResponse Namespace URI =
		 * http://messages.net.sms.wisdom.com/xsd Namespace Prefix = ns1
		 

		*//**
		 * field for OInsuranceExtension
		 *//*

		protected OInsuranceExtension localOInsuranceExtension;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localOInsuranceExtensionTracker = false;

		public boolean isOInsuranceExtensionSpecified() {
			return localOInsuranceExtensionTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return OInsuranceExtension
		 *//*
		public OInsuranceExtension getOInsuranceExtension() {
			return localOInsuranceExtension;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            OInsuranceExtension
		 *//*
		public void setOInsuranceExtension(OInsuranceExtension param) {
			localOInsuranceExtensionTracker = true;

			this.localOInsuranceExtension = param;

		}

		*//**
		 * field for TransResult
		 *//*

		protected TransResult localTransResult;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localTransResultTracker = false;

		public boolean isTransResultSpecified() {
			return localTransResultTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return TransResult
		 *//*
		public TransResult getTransResult() {
			return localTransResult;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TransResult
		 *//*
		public void setTransResult(TransResult param) {
			localTransResultTracker = true;

			this.localTransResult = param;

		}

		*//**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 *//*
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {

			org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
					this, parentQName);
			return factory.createOMElement(dataSource, parentQName);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {

			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			java.lang.String namespacePrefix = registerPrefix(xmlWriter,
					"http://messages.net.sms.wisdom.com/xsd");
			if ((namespacePrefix != null)
					&& (namespacePrefix.trim().length() > 0)) {
				writeAttribute("xsi",
						"http://www.w3.org/2001/XMLSchema-instance", "type",
						namespacePrefix + ":TXInsuranceResponse", xmlWriter);
			} else {
				writeAttribute("xsi",
						"http://www.w3.org/2001/XMLSchema-instance", "type",
						"TXInsuranceResponse", xmlWriter);
			}

			if (localTransExeDateTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "transExeDate", xmlWriter);

				if (localTransExeDate == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localTransExeDate);

				}

				xmlWriter.writeEndElement();
			}
			if (localTransExeTimeTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "transExeTime", xmlWriter);

				if (localTransExeTime == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localTransExeTime);

				}

				xmlWriter.writeEndElement();
			}
			if (localTransRefGUIDTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "transRefGUID", xmlWriter);

				if (localTransRefGUID == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localTransRefGUID);

				}

				xmlWriter.writeEndElement();
			}
			if (localTransSubTypeTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "transSubType", xmlWriter);

				if (localTransSubType == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localTransSubType);

				}

				xmlWriter.writeEndElement();
			}
			if (localTransTypeTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "transType", xmlWriter);

				if (localTransType == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localTransType);

				}

				xmlWriter.writeEndElement();
			}
			if (localOInsuranceExtensionTracker) {
				if (localOInsuranceExtension == null) {

					writeStartElement(null,
							"http://messages.net.sms.wisdom.com/xsd",
							"oInsuranceExtension", xmlWriter);

					// write the nil attribute
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);
					xmlWriter.writeEndElement();
				} else {
					localOInsuranceExtension.serialize(
							new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"oInsuranceExtension"), xmlWriter);
				}
			}
			if (localTransResultTracker) {
				if (localTransResult == null) {

					writeStartElement(null,
							"http://messages.net.sms.wisdom.com/xsd",
							"transResult", xmlWriter);

					// write the nil attribute
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);
					xmlWriter.writeEndElement();
				} else {
					localTransResult.serialize(new javax.xml.namespace.QName(
							"http://messages.net.sms.wisdom.com/xsd",
							"transResult"), xmlWriter);
				}
			}
			xmlWriter.writeEndElement();

		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("http://messages.net.sms.wisdom.com/xsd")) {
				return "ns1";
			}
			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		*//**
		 * Utility method to write an element start tag.
		 *//*
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		*//**
		 * Util method to write an attribute with the ns prefix
		 *//*
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			java.lang.String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		*//**
		 * method to handle Qnames
		 *//*

		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}

			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		*//**
		 * Register a namespace prefix
		 *//*
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		*//**
		 * databinding method to get an XML representation of this object
		 * 
		 *//*
		public javax.xml.stream.XMLStreamReader getPullParser(
				javax.xml.namespace.QName qName)
				throws org.apache.axis2.databinding.ADBException {

			java.util.ArrayList elementList = new java.util.ArrayList();
			java.util.ArrayList attribList = new java.util.ArrayList();

			attribList.add(new javax.xml.namespace.QName(
					"http://www.w3.org/2001/XMLSchema-instance", "type"));
			attribList.add(new javax.xml.namespace.QName(
					"http://messages.net.sms.wisdom.com/xsd",
					"TXInsuranceResponse"));
			if (localTransExeDateTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd",
						"transExeDate"));

				elementList.add(localTransExeDate == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localTransExeDate));
			}
			if (localTransExeTimeTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd",
						"transExeTime"));

				elementList.add(localTransExeTime == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localTransExeTime));
			}
			if (localTransRefGUIDTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd",
						"transRefGUID"));

				elementList.add(localTransRefGUID == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localTransRefGUID));
			}
			if (localTransSubTypeTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd",
						"transSubType"));

				elementList.add(localTransSubType == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localTransSubType));
			}
			if (localTransTypeTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "transType"));

				elementList.add(localTransType == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localTransType));
			}
			if (localOInsuranceExtensionTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd",
						"oInsuranceExtension"));

				elementList.add(localOInsuranceExtension == null ? null
						: localOInsuranceExtension);
			}
			if (localTransResultTracker) {
				elementList
						.add(new javax.xml.namespace.QName(
								"http://messages.net.sms.wisdom.com/xsd",
								"transResult"));

				elementList.add(localTransResult == null ? null
						: localTransResult);
			}

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(
					qName, elementList.toArray(), attribList.toArray());

		}

		*//**
		 * Factory class that keeps the parse method
		 *//*
		public static class Factory {

			*//**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 *//*
			public static TXInsuranceResponse parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				TXInsuranceResponse object = new TXInsuranceResponse();

				int event;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");
						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"TXInsuranceResponse".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);
								return (TXInsuranceResponse) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"transExeDate").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setTransExeDate(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"transExeTime").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setTransExeTime(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"transRefGUID").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setTransRefGUID(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"transSubType").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setTransSubType(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"transType").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setTransType(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"oInsuranceExtension").equals(reader
									.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if ("true".equals(nillableValue)
								|| "1".equals(nillableValue)) {
							object.setOInsuranceExtension(null);
							reader.next();

							reader.next();

						} else {

							object.setOInsuranceExtension(OInsuranceExtension.Factory
									.parse(reader));

							reader.next();
						}
					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"transResult").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if ("true".equals(nillableValue)
								|| "1".equals(nillableValue)) {
							object.setTransResult(null);
							reader.next();

							reader.next();

						} else {

							object.setTransResult(TransResult.Factory
									.parse(reader));

							reader.next();
						}
					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());

				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class OInsuranceExtension implements
			org.apache.axis2.databinding.ADBBean {
		
		 * This type was generated from the piece of schema that had name =
		 * OInsuranceExtension Namespace URI =
		 * http://messages.net.sms.wisdom.com/xsd Namespace Prefix = ns1
		 

		*//**
		 * field for MaxRecords
		 *//*

		protected java.lang.String localMaxRecords;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localMaxRecordsTracker = false;

		public boolean isMaxRecordsSpecified() {
			return localMaxRecordsTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getMaxRecords() {
			return localMaxRecords;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            MaxRecords
		 *//*
		public void setMaxRecords(java.lang.String param) {
			localMaxRecordsTracker = true;

			this.localMaxRecords = param;

		}

		*//**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 *//*
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {

			org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
					this, parentQName);
			return factory.createOMElement(dataSource, parentQName);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {

			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"http://messages.net.sms.wisdom.com/xsd");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":OInsuranceExtension",
							xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "OInsuranceExtension", xmlWriter);
				}

			}
			if (localMaxRecordsTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "maxRecords", xmlWriter);

				if (localMaxRecords == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localMaxRecords);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();

		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("http://messages.net.sms.wisdom.com/xsd")) {
				return "ns1";
			}
			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		*//**
		 * Utility method to write an element start tag.
		 *//*
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		*//**
		 * Util method to write an attribute with the ns prefix
		 *//*
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			java.lang.String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		*//**
		 * method to handle Qnames
		 *//*

		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}

			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		*//**
		 * Register a namespace prefix
		 *//*
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		*//**
		 * databinding method to get an XML representation of this object
		 * 
		 *//*
		public javax.xml.stream.XMLStreamReader getPullParser(
				javax.xml.namespace.QName qName)
				throws org.apache.axis2.databinding.ADBException {

			java.util.ArrayList elementList = new java.util.ArrayList();
			java.util.ArrayList attribList = new java.util.ArrayList();

			if (localMaxRecordsTracker) {
				elementList
						.add(new javax.xml.namespace.QName(
								"http://messages.net.sms.wisdom.com/xsd",
								"maxRecords"));

				elementList.add(localMaxRecords == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localMaxRecords));
			}

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(
					qName, elementList.toArray(), attribList.toArray());

		}

		*//**
		 * Factory class that keeps the parse method
		 *//*
		public static class Factory {

			*//**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 *//*
			public static OInsuranceExtension parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				OInsuranceExtension object = new OInsuranceExtension();

				int event;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");
						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"OInsuranceExtension".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);
								return (OInsuranceExtension) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"maxRecords").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setMaxRecords(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());

				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	@SuppressWarnings("serial")
	public static class TXInsuranceRequestExtension extends
			TXInsuranceExtension implements
			org.apache.axis2.databinding.ADBBean {
		
		 * This type was generated from the piece of schema that had name =
		 * TXInsuranceRequestExtension Namespace URI =
		 * http://messages.net.sms.wisdom.com/xsd Namespace Prefix = ns1
		 

		*//**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 *//*
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {

			org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
					this, parentQName);
			return factory.createOMElement(dataSource, parentQName);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {

			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			java.lang.String namespacePrefix = registerPrefix(xmlWriter,
					"http://messages.net.sms.wisdom.com/xsd");
			if ((namespacePrefix != null)
					&& (namespacePrefix.trim().length() > 0)) {
				writeAttribute("xsi",
						"http://www.w3.org/2001/XMLSchema-instance", "type",
						namespacePrefix + ":TXInsuranceRequestExtension",
						xmlWriter);
			} else {
				writeAttribute("xsi",
						"http://www.w3.org/2001/XMLSchema-instance", "type",
						"TXInsuranceRequestExtension", xmlWriter);
			}

			if (localOperatorTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "operator", xmlWriter);

				if (localOperator == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localOperator);

				}

				xmlWriter.writeEndElement();
			}
			if (localOperatorKeyTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "operatorKey", xmlWriter);

				if (localOperatorKey == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localOperatorKey);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();

		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("http://messages.net.sms.wisdom.com/xsd")) {
				return "ns1";
			}
			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		*//**
		 * Utility method to write an element start tag.
		 *//*
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		*//**
		 * Util method to write an attribute with the ns prefix
		 *//*
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			java.lang.String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		*//**
		 * method to handle Qnames
		 *//*

		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}

			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		*//**
		 * Register a namespace prefix
		 *//*
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		*//**
		 * databinding method to get an XML representation of this object
		 * 
		 *//*
		public javax.xml.stream.XMLStreamReader getPullParser(
				javax.xml.namespace.QName qName)
				throws org.apache.axis2.databinding.ADBException {

			java.util.ArrayList elementList = new java.util.ArrayList();
			java.util.ArrayList attribList = new java.util.ArrayList();

			attribList.add(new javax.xml.namespace.QName(
					"http://www.w3.org/2001/XMLSchema-instance", "type"));
			attribList.add(new javax.xml.namespace.QName(
					"http://messages.net.sms.wisdom.com/xsd",
					"TXInsuranceRequestExtension"));
			if (localOperatorTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "operator"));

				elementList.add(localOperator == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localOperator));
			}
			if (localOperatorKeyTracker) {
				elementList
						.add(new javax.xml.namespace.QName(
								"http://messages.net.sms.wisdom.com/xsd",
								"operatorKey"));

				elementList.add(localOperatorKey == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localOperatorKey));
			}

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(
					qName, elementList.toArray(), attribList.toArray());

		}

		*//**
		 * Factory class that keeps the parse method
		 *//*
		public static class Factory {

			*//**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 *//*
			public static TXInsuranceRequestExtension parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				TXInsuranceRequestExtension object = new TXInsuranceRequestExtension();

				int event;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");
						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"TXInsuranceRequestExtension".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);
								return (TXInsuranceRequestExtension) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"operator").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setOperator(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"operatorKey").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setOperatorKey(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());

				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class TXInsuranceResponseExtension extends
			TXInsuranceExtension implements
			org.apache.axis2.databinding.ADBBean {
		
		 * This type was generated from the piece of schema that had name =
		 * TXInsuranceResponseExtension Namespace URI =
		 * http://messages.net.sms.wisdom.com/xsd Namespace Prefix = ns1
		 

		*//**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 *//*
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {

			org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
					this, parentQName);
			return factory.createOMElement(dataSource, parentQName);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {

			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			java.lang.String namespacePrefix = registerPrefix(xmlWriter,
					"http://messages.net.sms.wisdom.com/xsd");
			if ((namespacePrefix != null)
					&& (namespacePrefix.trim().length() > 0)) {
				writeAttribute("xsi",
						"http://www.w3.org/2001/XMLSchema-instance", "type",
						namespacePrefix + ":TXInsuranceResponseExtension",
						xmlWriter);
			} else {
				writeAttribute("xsi",
						"http://www.w3.org/2001/XMLSchema-instance", "type",
						"TXInsuranceResponseExtension", xmlWriter);
			}

			if (localOperatorTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "operator", xmlWriter);

				if (localOperator == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localOperator);

				}

				xmlWriter.writeEndElement();
			}
			if (localOperatorKeyTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "operatorKey", xmlWriter);

				if (localOperatorKey == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localOperatorKey);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();

		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("http://messages.net.sms.wisdom.com/xsd")) {
				return "ns1";
			}
			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		*//**
		 * Utility method to write an element start tag.
		 *//*
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		*//**
		 * Util method to write an attribute with the ns prefix
		 *//*
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			java.lang.String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		*//**
		 * method to handle Qnames
		 *//*

		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}

			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		*//**
		 * Register a namespace prefix
		 *//*
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		*//**
		 * databinding method to get an XML representation of this object
		 * 
		 *//*
		public javax.xml.stream.XMLStreamReader getPullParser(
				javax.xml.namespace.QName qName)
				throws org.apache.axis2.databinding.ADBException {

			java.util.ArrayList elementList = new java.util.ArrayList();
			java.util.ArrayList attribList = new java.util.ArrayList();

			attribList.add(new javax.xml.namespace.QName(
					"http://www.w3.org/2001/XMLSchema-instance", "type"));
			attribList.add(new javax.xml.namespace.QName(
					"http://messages.net.sms.wisdom.com/xsd",
					"TXInsuranceResponseExtension"));
			if (localOperatorTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "operator"));

				elementList.add(localOperator == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localOperator));
			}
			if (localOperatorKeyTracker) {
				elementList
						.add(new javax.xml.namespace.QName(
								"http://messages.net.sms.wisdom.com/xsd",
								"operatorKey"));

				elementList.add(localOperatorKey == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localOperatorKey));
			}

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(
					qName, elementList.toArray(), attribList.toArray());

		}

		*//**
		 * Factory class that keeps the parse method
		 *//*
		public static class Factory {

			*//**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 *//*
			public static TXInsuranceResponseExtension parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				TXInsuranceResponseExtension object = new TXInsuranceResponseExtension();

				int event;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");
						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"TXInsuranceResponseExtension".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);
								return (TXInsuranceResponseExtension) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"operator").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setOperator(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"operatorKey").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setOperatorKey(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());

				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class TaskTarget implements
			org.apache.axis2.databinding.ADBBean {
		
		 * This type was generated from the piece of schema that had name =
		 * TaskTarget Namespace URI = http://messages.net.sms.wisdom.com/xsd
		 * Namespace Prefix = ns1
		 

		*//**
		 * field for Receiver
		 *//*

		protected java.lang.String localReceiver;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localReceiverTracker = false;

		public boolean isReceiverSpecified() {
			return localReceiverTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getReceiver() {
			return localReceiver;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Receiver
		 *//*
		public void setReceiver(java.lang.String param) {
			localReceiverTracker = true;

			this.localReceiver = param;

		}

		*//**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 *//*
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {

			org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
					this, parentQName);
			return factory.createOMElement(dataSource, parentQName);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {

			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"http://messages.net.sms.wisdom.com/xsd");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":TaskTarget", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "TaskTarget", xmlWriter);
				}

			}
			if (localReceiverTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "receiver", xmlWriter);

				if (localReceiver == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localReceiver);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();

		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("http://messages.net.sms.wisdom.com/xsd")) {
				return "ns1";
			}
			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		*//**
		 * Utility method to write an element start tag.
		 *//*
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		*//**
		 * Util method to write an attribute with the ns prefix
		 *//*
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			java.lang.String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		*//**
		 * method to handle Qnames
		 *//*

		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}

			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		*//**
		 * Register a namespace prefix
		 *//*
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		*//**
		 * databinding method to get an XML representation of this object
		 * 
		 *//*
		public javax.xml.stream.XMLStreamReader getPullParser(
				javax.xml.namespace.QName qName)
				throws org.apache.axis2.databinding.ADBException {

			java.util.ArrayList elementList = new java.util.ArrayList();
			java.util.ArrayList attribList = new java.util.ArrayList();

			if (localReceiverTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "receiver"));

				elementList.add(localReceiver == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localReceiver));
			}

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(
					qName, elementList.toArray(), attribList.toArray());

		}

		*//**
		 * Factory class that keeps the parse method
		 *//*
		public static class Factory {

			*//**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 *//*
			public static TaskTarget parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				TaskTarget object = new TaskTarget();

				int event;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");
						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"TaskTarget".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);
								return (TaskTarget) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"receiver").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setReceiver(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());

				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class IInsuranceExtension implements
			org.apache.axis2.databinding.ADBBean {
		
		 * This type was generated from the piece of schema that had name =
		 * IInsuranceExtension Namespace URI =
		 * http://messages.net.sms.wisdom.com/xsd Namespace Prefix = ns1
		 

		*//**
		 * field for MaxRecords
		 *//*

		protected java.lang.String localMaxRecords;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localMaxRecordsTracker = false;

		public boolean isMaxRecordsSpecified() {
			return localMaxRecordsTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getMaxRecords() {
			return localMaxRecords;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            MaxRecords
		 *//*
		public void setMaxRecords(java.lang.String param) {
			localMaxRecordsTracker = true;

			this.localMaxRecords = param;

		}

		*//**
		 * field for OrderField
		 *//*

		protected java.lang.String localOrderField;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localOrderFieldTracker = false;

		public boolean isOrderFieldSpecified() {
			return localOrderFieldTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getOrderField() {
			return localOrderField;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            OrderField
		 *//*
		public void setOrderField(java.lang.String param) {
			localOrderFieldTracker = true;

			this.localOrderField = param;

		}

		*//**
		 * field for OrderFlag
		 *//*

		protected java.lang.String localOrderFlag;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localOrderFlagTracker = false;

		public boolean isOrderFlagSpecified() {
			return localOrderFlagTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getOrderFlag() {
			return localOrderFlag;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            OrderFlag
		 *//*
		public void setOrderFlag(java.lang.String param) {
			localOrderFlagTracker = true;

			this.localOrderFlag = param;

		}

		*//**
		 * field for PageFlag
		 *//*

		protected java.lang.String localPageFlag;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localPageFlagTracker = false;

		public boolean isPageFlagSpecified() {
			return localPageFlagTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getPageFlag() {
			return localPageFlag;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            PageFlag
		 *//*
		public void setPageFlag(java.lang.String param) {
			localPageFlagTracker = true;

			this.localPageFlag = param;

		}

		*//**
		 * field for PageRowNum
		 *//*

		protected java.lang.String localPageRowNum;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localPageRowNumTracker = false;

		public boolean isPageRowNumSpecified() {
			return localPageRowNumTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getPageRowNum() {
			return localPageRowNum;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            PageRowNum
		 *//*
		public void setPageRowNum(java.lang.String param) {
			localPageRowNumTracker = true;

			this.localPageRowNum = param;

		}

		*//**
		 * field for RowNumStart
		 *//*

		protected java.lang.String localRowNumStart;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localRowNumStartTracker = false;

		public boolean isRowNumStartSpecified() {
			return localRowNumStartTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getRowNumStart() {
			return localRowNumStart;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            RowNumStart
		 *//*
		public void setRowNumStart(java.lang.String param) {
			localRowNumStartTracker = true;

			this.localRowNumStart = param;

		}

		*//**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 *//*
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {

			org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
					this, parentQName);
			return factory.createOMElement(dataSource, parentQName);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {

			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"http://messages.net.sms.wisdom.com/xsd");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":IInsuranceExtension",
							xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "IInsuranceExtension", xmlWriter);
				}

			}
			if (localMaxRecordsTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "maxRecords", xmlWriter);

				if (localMaxRecords == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localMaxRecords);

				}

				xmlWriter.writeEndElement();
			}
			if (localOrderFieldTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "orderField", xmlWriter);

				if (localOrderField == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localOrderField);

				}

				xmlWriter.writeEndElement();
			}
			if (localOrderFlagTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "orderFlag", xmlWriter);

				if (localOrderFlag == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localOrderFlag);

				}

				xmlWriter.writeEndElement();
			}
			if (localPageFlagTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "pageFlag", xmlWriter);

				if (localPageFlag == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localPageFlag);

				}

				xmlWriter.writeEndElement();
			}
			if (localPageRowNumTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "pageRowNum", xmlWriter);

				if (localPageRowNum == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localPageRowNum);

				}

				xmlWriter.writeEndElement();
			}
			if (localRowNumStartTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "rowNumStart", xmlWriter);

				if (localRowNumStart == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localRowNumStart);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();

		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("http://messages.net.sms.wisdom.com/xsd")) {
				return "ns1";
			}
			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		*//**
		 * Utility method to write an element start tag.
		 *//*
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		*//**
		 * Util method to write an attribute with the ns prefix
		 *//*
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			java.lang.String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		*//**
		 * method to handle Qnames
		 *//*

		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}

			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		*//**
		 * Register a namespace prefix
		 *//*
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		*//**
		 * databinding method to get an XML representation of this object
		 * 
		 *//*
		public javax.xml.stream.XMLStreamReader getPullParser(
				javax.xml.namespace.QName qName)
				throws org.apache.axis2.databinding.ADBException {

			java.util.ArrayList elementList = new java.util.ArrayList();
			java.util.ArrayList attribList = new java.util.ArrayList();

			if (localMaxRecordsTracker) {
				elementList
						.add(new javax.xml.namespace.QName(
								"http://messages.net.sms.wisdom.com/xsd",
								"maxRecords"));

				elementList.add(localMaxRecords == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localMaxRecords));
			}
			if (localOrderFieldTracker) {
				elementList
						.add(new javax.xml.namespace.QName(
								"http://messages.net.sms.wisdom.com/xsd",
								"orderField"));

				elementList.add(localOrderField == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localOrderField));
			}
			if (localOrderFlagTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "orderFlag"));

				elementList.add(localOrderFlag == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localOrderFlag));
			}
			if (localPageFlagTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "pageFlag"));

				elementList.add(localPageFlag == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localPageFlag));
			}
			if (localPageRowNumTracker) {
				elementList
						.add(new javax.xml.namespace.QName(
								"http://messages.net.sms.wisdom.com/xsd",
								"pageRowNum"));

				elementList.add(localPageRowNum == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localPageRowNum));
			}
			if (localRowNumStartTracker) {
				elementList
						.add(new javax.xml.namespace.QName(
								"http://messages.net.sms.wisdom.com/xsd",
								"rowNumStart"));

				elementList.add(localRowNumStart == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localRowNumStart));
			}

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(
					qName, elementList.toArray(), attribList.toArray());

		}

		*//**
		 * Factory class that keeps the parse method
		 *//*
		public static class Factory {

			*//**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 *//*
			public static IInsuranceExtension parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				IInsuranceExtension object = new IInsuranceExtension();

				int event;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");
						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"IInsuranceExtension".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);
								return (IInsuranceExtension) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"maxRecords").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setMaxRecords(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"orderField").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setOrderField(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"orderFlag").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setOrderFlag(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"pageFlag").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setPageFlag(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"pageRowNum").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setPageRowNum(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"rowNumStart").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setRowNumStart(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());

				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class TXInsuranceRequest extends TXInsurance implements
			org.apache.axis2.databinding.ADBBean {
		
		 * This type was generated from the piece of schema that had name =
		 * TXInsuranceRequest Namespace URI =
		 * http://messages.net.sms.wisdom.com/xsd Namespace Prefix = ns1
		 

		*//**
		 * field for IInsuranceExtension
		 *//*

		protected IInsuranceExtension localIInsuranceExtension;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localIInsuranceExtensionTracker = false;

		public boolean isIInsuranceExtensionSpecified() {
			return localIInsuranceExtensionTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return IInsuranceExtension
		 *//*
		public IInsuranceExtension getIInsuranceExtension() {
			return localIInsuranceExtension;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            IInsuranceExtension
		 *//*
		public void setIInsuranceExtension(IInsuranceExtension param) {
			localIInsuranceExtensionTracker = true;

			this.localIInsuranceExtension = param;

		}

		*//**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 *//*
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {

			org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
					this, parentQName);
			return factory.createOMElement(dataSource, parentQName);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {

			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			java.lang.String namespacePrefix = registerPrefix(xmlWriter,
					"http://messages.net.sms.wisdom.com/xsd");
			if ((namespacePrefix != null)
					&& (namespacePrefix.trim().length() > 0)) {
				writeAttribute("xsi",
						"http://www.w3.org/2001/XMLSchema-instance", "type",
						namespacePrefix + ":TXInsuranceRequest", xmlWriter);
			} else {
				writeAttribute("xsi",
						"http://www.w3.org/2001/XMLSchema-instance", "type",
						"TXInsuranceRequest", xmlWriter);
			}

			if (localTransExeDateTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "transExeDate", xmlWriter);

				if (localTransExeDate == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localTransExeDate);

				}

				xmlWriter.writeEndElement();
			}
			if (localTransExeTimeTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "transExeTime", xmlWriter);

				if (localTransExeTime == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localTransExeTime);

				}

				xmlWriter.writeEndElement();
			}
			if (localTransRefGUIDTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "transRefGUID", xmlWriter);

				if (localTransRefGUID == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localTransRefGUID);

				}

				xmlWriter.writeEndElement();
			}
			if (localTransSubTypeTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "transSubType", xmlWriter);

				if (localTransSubType == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localTransSubType);

				}

				xmlWriter.writeEndElement();
			}
			if (localTransTypeTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "transType", xmlWriter);

				if (localTransType == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localTransType);

				}

				xmlWriter.writeEndElement();
			}
			if (localIInsuranceExtensionTracker) {
				if (localIInsuranceExtension == null) {

					writeStartElement(null,
							"http://messages.net.sms.wisdom.com/xsd",
							"IInsuranceExtension", xmlWriter);

					// write the nil attribute
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);
					xmlWriter.writeEndElement();
				} else {
					localIInsuranceExtension.serialize(
							new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"IInsuranceExtension"), xmlWriter);
				}
			}
			xmlWriter.writeEndElement();

		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("http://messages.net.sms.wisdom.com/xsd")) {
				return "ns1";
			}
			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		*//**
		 * Utility method to write an element start tag.
		 *//*
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		*//**
		 * Util method to write an attribute with the ns prefix
		 *//*
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			java.lang.String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		*//**
		 * method to handle Qnames
		 *//*

		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}

			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		*//**
		 * Register a namespace prefix
		 *//*
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		*//**
		 * databinding method to get an XML representation of this object
		 * 
		 *//*
		public javax.xml.stream.XMLStreamReader getPullParser(
				javax.xml.namespace.QName qName)
				throws org.apache.axis2.databinding.ADBException {

			java.util.ArrayList elementList = new java.util.ArrayList();
			java.util.ArrayList attribList = new java.util.ArrayList();

			attribList.add(new javax.xml.namespace.QName(
					"http://www.w3.org/2001/XMLSchema-instance", "type"));
			attribList.add(new javax.xml.namespace.QName(
					"http://messages.net.sms.wisdom.com/xsd",
					"TXInsuranceRequest"));
			if (localTransExeDateTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd",
						"transExeDate"));

				elementList.add(localTransExeDate == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localTransExeDate));
			}
			if (localTransExeTimeTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd",
						"transExeTime"));

				elementList.add(localTransExeTime == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localTransExeTime));
			}
			if (localTransRefGUIDTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd",
						"transRefGUID"));

				elementList.add(localTransRefGUID == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localTransRefGUID));
			}
			if (localTransSubTypeTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd",
						"transSubType"));

				elementList.add(localTransSubType == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localTransSubType));
			}
			if (localTransTypeTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "transType"));

				elementList.add(localTransType == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localTransType));
			}
			if (localIInsuranceExtensionTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd",
						"IInsuranceExtension"));

				elementList.add(localIInsuranceExtension == null ? null
						: localIInsuranceExtension);
			}

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(
					qName, elementList.toArray(), attribList.toArray());

		}

		*//**
		 * Factory class that keeps the parse method
		 *//*
		public static class Factory {

			*//**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 *//*
			public static TXInsuranceRequest parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				TXInsuranceRequest object = new TXInsuranceRequest();

				int event;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");
						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"TXInsuranceRequest".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);
								return (TXInsuranceRequest) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"transExeDate").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setTransExeDate(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"transExeTime").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setTransExeTime(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"transRefGUID").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setTransRefGUID(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"transSubType").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setTransSubType(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"transType").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setTransType(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"IInsuranceExtension").equals(reader
									.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if ("true".equals(nillableValue)
								|| "1".equals(nillableValue)) {
							object.setIInsuranceExtension(null);
							reader.next();

							reader.next();

						} else {

							object.setIInsuranceExtension(IInsuranceExtension.Factory
									.parse(reader));

							reader.next();
						}
					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());

				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class ExtensionMapper {

		public static java.lang.Object getTypeObject(
				java.lang.String namespaceURI, java.lang.String typeName,
				javax.xml.stream.XMLStreamReader reader)
				throws java.lang.Exception {

			if ("http://messages.net.sms.wisdom.com/xsd".equals(namespaceURI)
					&& "TXInsuranceResponse".equals(typeName)) {

				return TXInsuranceResponse.Factory.parse(reader);

			}

			if ("http://messages.net.sms.wisdom.com/xsd".equals(namespaceURI)
					&& "OInsuranceExtension".equals(typeName)) {

				return OInsuranceExtension.Factory.parse(reader);

			}

			if ("http://messages.net.sms.wisdom.com/xsd".equals(namespaceURI)
					&& "TXInsuranceRequestExtension".equals(typeName)) {

				return TXInsuranceRequestExtension.Factory.parse(reader);

			}

			if ("http://messages.net.sms.wisdom.com/xsd".equals(namespaceURI)
					&& "TXInsuranceResponseExtension".equals(typeName)) {

				return TXInsuranceResponseExtension.Factory.parse(reader);

			}

			if ("http://messages.net.sms.wisdom.com/xsd".equals(namespaceURI)
					&& "TaskTarget".equals(typeName)) {

				return TaskTarget.Factory.parse(reader);

			}

			if ("http://messages.net.sms.wisdom.com/xsd".equals(namespaceURI)
					&& "IInsuranceExtension".equals(typeName)) {

				return IInsuranceExtension.Factory.parse(reader);

			}

			if ("http://messages.net.sms.wisdom.com/xsd".equals(namespaceURI)
					&& "TXInsuranceRequest".equals(typeName)) {

				return TXInsuranceRequest.Factory.parse(reader);

			}

			if ("http://messages.net.sms.wisdom.com/xsd".equals(namespaceURI)
					&& "Label".equals(typeName)) {

				return Label.Factory.parse(reader);

			}

			if ("http://messages.net.sms.wisdom.com/xsd".equals(namespaceURI)
					&& "SmsMessage".equals(typeName)) {

				return SmsMessage.Factory.parse(reader);

			}

			if ("http://messages.net.sms.wisdom.com/xsd".equals(namespaceURI)
					&& "TransResult".equals(typeName)) {

				return TransResult.Factory.parse(reader);

			}

			if ("http://messages.net.sms.wisdom.com/xsd".equals(namespaceURI)
					&& "TXInsuranceExtension".equals(typeName)) {

				return TXInsuranceExtension.Factory.parse(reader);

			}

			if ("http://messages.net.sms.wisdom.com/xsd".equals(namespaceURI)
					&& "TXInsurance".equals(typeName)) {

				return TXInsurance.Factory.parse(reader);

			}

			if ("http://messages.net.sms.wisdom.com/xsd".equals(namespaceURI)
					&& "SmsMessages".equals(typeName)) {

				return SmsMessages.Factory.parse(reader);

			}

			if ("http://http.net.sms.wisdom.com/xsd".equals(namespaceURI)
					&& "Response".equals(typeName)) {

				return Response.Factory.parse(reader);

			}

			if ("http://messages.net.sms.wisdom.com/xsd".equals(namespaceURI)
					&& "SpreadTask".equals(typeName)) {

				return SpreadTask.Factory.parse(reader);

			}

			throw new org.apache.axis2.databinding.ADBException(
					"Unsupported type " + namespaceURI + " " + typeName);
		}

	}

	public static class Label implements org.apache.axis2.databinding.ADBBean {
		
		 * This type was generated from the piece of schema that had name =
		 * Label Namespace URI = http://messages.net.sms.wisdom.com/xsd
		 * Namespace Prefix = ns1
		 

		*//**
		 * field for LabelName
		 *//*

		protected java.lang.String localLabelName;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localLabelNameTracker = false;

		public boolean isLabelNameSpecified() {
			return localLabelNameTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getLabelName() {
			return localLabelName;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            LabelName
		 *//*
		public void setLabelName(java.lang.String param) {
			localLabelNameTracker = true;

			this.localLabelName = param;

		}

		*//**
		 * field for LabelValue
		 *//*

		protected java.lang.String localLabelValue;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localLabelValueTracker = false;

		public boolean isLabelValueSpecified() {
			return localLabelValueTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getLabelValue() {
			return localLabelValue;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            LabelValue
		 *//*
		public void setLabelValue(java.lang.String param) {
			localLabelValueTracker = true;

			this.localLabelValue = param;

		}

		*//**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 *//*
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {

			org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
					this, parentQName);
			return factory.createOMElement(dataSource, parentQName);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {

			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"http://messages.net.sms.wisdom.com/xsd");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":Label", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "Label", xmlWriter);
				}

			}
			if (localLabelNameTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "labelName", xmlWriter);

				if (localLabelName == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localLabelName);

				}

				xmlWriter.writeEndElement();
			}
			if (localLabelValueTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "labelValue", xmlWriter);

				if (localLabelValue == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localLabelValue);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();

		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("http://messages.net.sms.wisdom.com/xsd")) {
				return "ns1";
			}
			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		*//**
		 * Utility method to write an element start tag.
		 *//*
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		*//**
		 * Util method to write an attribute with the ns prefix
		 *//*
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			java.lang.String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		*//**
		 * method to handle Qnames
		 *//*

		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}

			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		*//**
		 * Register a namespace prefix
		 *//*
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		*//**
		 * databinding method to get an XML representation of this object
		 * 
		 *//*
		public javax.xml.stream.XMLStreamReader getPullParser(
				javax.xml.namespace.QName qName)
				throws org.apache.axis2.databinding.ADBException {

			java.util.ArrayList elementList = new java.util.ArrayList();
			java.util.ArrayList attribList = new java.util.ArrayList();

			if (localLabelNameTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "labelName"));

				elementList.add(localLabelName == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localLabelName));
			}
			if (localLabelValueTracker) {
				elementList
						.add(new javax.xml.namespace.QName(
								"http://messages.net.sms.wisdom.com/xsd",
								"labelValue"));

				elementList.add(localLabelValue == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localLabelValue));
			}

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(
					qName, elementList.toArray(), attribList.toArray());

		}

		*//**
		 * Factory class that keeps the parse method
		 *//*
		public static class Factory {

			*//**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 *//*
			public static Label parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Label object = new Label();

				int event;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");
						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"Label".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);
								return (Label) ExtensionMapper.getTypeObject(
										nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"labelName").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setLabelName(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"labelValue").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setLabelValue(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());

				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class TransResult implements
			org.apache.axis2.databinding.ADBBean {
		
		 * This type was generated from the piece of schema that had name =
		 * TransResult Namespace URI = http://messages.net.sms.wisdom.com/xsd
		 * Namespace Prefix = ns1
		 

		*//**
		 * field for ResultCode
		 *//*

		protected java.lang.String localResultCode;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localResultCodeTracker = false;

		public boolean isResultCodeSpecified() {
			return localResultCodeTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getResultCode() {
			return localResultCode;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            ResultCode
		 *//*
		public void setResultCode(java.lang.String param) {
			localResultCodeTracker = true;

			this.localResultCode = param;

		}

		*//**
		 * field for ResultInfoDesc
		 *//*

		protected java.lang.String localResultInfoDesc;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localResultInfoDescTracker = false;

		public boolean isResultInfoDescSpecified() {
			return localResultInfoDescTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getResultInfoDesc() {
			return localResultInfoDesc;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            ResultInfoDesc
		 *//*
		public void setResultInfoDesc(java.lang.String param) {
			localResultInfoDescTracker = true;

			this.localResultInfoDesc = param;

		}

		*//**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 *//*
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {

			org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
					this, parentQName);
			return factory.createOMElement(dataSource, parentQName);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {

			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"http://messages.net.sms.wisdom.com/xsd");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":TransResult", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "TransResult", xmlWriter);
				}

			}
			if (localResultCodeTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "resultCode", xmlWriter);

				if (localResultCode == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localResultCode);

				}

				xmlWriter.writeEndElement();
			}
			if (localResultInfoDescTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "resultInfoDesc", xmlWriter);

				if (localResultInfoDesc == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localResultInfoDesc);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();

		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("http://messages.net.sms.wisdom.com/xsd")) {
				return "ns1";
			}
			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		*//**
		 * Utility method to write an element start tag.
		 *//*
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		*//**
		 * Util method to write an attribute with the ns prefix
		 *//*
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			java.lang.String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		*//**
		 * method to handle Qnames
		 *//*

		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}

			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		*//**
		 * Register a namespace prefix
		 *//*
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		*//**
		 * databinding method to get an XML representation of this object
		 * 
		 *//*
		public javax.xml.stream.XMLStreamReader getPullParser(
				javax.xml.namespace.QName qName)
				throws org.apache.axis2.databinding.ADBException {

			java.util.ArrayList elementList = new java.util.ArrayList();
			java.util.ArrayList attribList = new java.util.ArrayList();

			if (localResultCodeTracker) {
				elementList
						.add(new javax.xml.namespace.QName(
								"http://messages.net.sms.wisdom.com/xsd",
								"resultCode"));

				elementList.add(localResultCode == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localResultCode));
			}
			if (localResultInfoDescTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd",
						"resultInfoDesc"));

				elementList.add(localResultInfoDesc == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localResultInfoDesc));
			}

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(
					qName, elementList.toArray(), attribList.toArray());

		}

		*//**
		 * Factory class that keeps the parse method
		 *//*
		public static class Factory {

			*//**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 *//*
			public static TransResult parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				TransResult object = new TransResult();

				int event;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");
						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"TransResult".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);
								return (TransResult) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"resultCode").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setResultCode(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"resultInfoDesc").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setResultInfoDesc(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());

				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class SendSMS implements org.apache.axis2.databinding.ADBBean {

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://http.net.sms.wisdom.com", "sendSMS", "ns3");

		*//**
		 * field for UserName
		 *//*

		protected java.lang.String localUserName;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localUserNameTracker = false;

		public boolean isUserNameSpecified() {
			return localUserNameTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getUserName() {
			return localUserName;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            UserName
		 *//*
		public void setUserName(java.lang.String param) {
			localUserNameTracker = true;

			this.localUserName = param;

		}

		*//**
		 * field for Password
		 *//*

		protected java.lang.String localPassword;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localPasswordTracker = false;

		public boolean isPasswordSpecified() {
			return localPasswordTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getPassword() {
			return localPassword;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Password
		 *//*
		public void setPassword(java.lang.String param) {
			localPasswordTracker = true;

			this.localPassword = param;

		}

		*//**
		 * field for Msgs
		 *//*

		protected SmsMessages localMsgs;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localMsgsTracker = false;

		public boolean isMsgsSpecified() {
			return localMsgsTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return SmsMessages
		 *//*
		public SmsMessages getMsgs() {
			return localMsgs;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Msgs
		 *//*
		public void setMsgs(SmsMessages param) {
			localMsgsTracker = true;

			this.localMsgs = param;

		}

		*//**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 *//*
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {

			org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
					this, MY_QNAME);
			return factory.createOMElement(dataSource, MY_QNAME);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {

			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"http://http.net.sms.wisdom.com");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":sendSMS", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "sendSMS", xmlWriter);
				}

			}
			if (localUserNameTracker) {
				namespace = "http://http.net.sms.wisdom.com";
				writeStartElement(null, namespace, "UserName", xmlWriter);

				if (localUserName == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localUserName);

				}

				xmlWriter.writeEndElement();
			}
			if (localPasswordTracker) {
				namespace = "http://http.net.sms.wisdom.com";
				writeStartElement(null, namespace, "Password", xmlWriter);

				if (localPassword == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localPassword);

				}

				xmlWriter.writeEndElement();
			}
			if (localMsgsTracker) {
				if (localMsgs == null) {

					writeStartElement(null, "http://http.net.sms.wisdom.com",
							"msgs", xmlWriter);

					// write the nil attribute
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);
					xmlWriter.writeEndElement();
				} else {
					localMsgs.serialize(new javax.xml.namespace.QName(
							"http://http.net.sms.wisdom.com", "msgs"),
							xmlWriter);
				}
			}
			xmlWriter.writeEndElement();

		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("http://http.net.sms.wisdom.com")) {
				return "ns3";
			}
			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		*//**
		 * Utility method to write an element start tag.
		 *//*
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		*//**
		 * Util method to write an attribute with the ns prefix
		 *//*
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			java.lang.String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		*//**
		 * method to handle Qnames
		 *//*

		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}

			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		*//**
		 * Register a namespace prefix
		 *//*
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		*//**
		 * databinding method to get an XML representation of this object
		 * 
		 *//*
		public javax.xml.stream.XMLStreamReader getPullParser(
				javax.xml.namespace.QName qName)
				throws org.apache.axis2.databinding.ADBException {

			java.util.ArrayList elementList = new java.util.ArrayList();
			java.util.ArrayList attribList = new java.util.ArrayList();

			if (localUserNameTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://http.net.sms.wisdom.com", "UserName"));

				elementList.add(localUserName == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localUserName));
			}
			if (localPasswordTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://http.net.sms.wisdom.com", "Password"));

				elementList.add(localPassword == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localPassword));
			}
			if (localMsgsTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://http.net.sms.wisdom.com", "msgs"));

				elementList.add(localMsgs == null ? null : localMsgs);
			}

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(
					qName, elementList.toArray(), attribList.toArray());

		}

		*//**
		 * Factory class that keeps the parse method
		 *//*
		public static class Factory {

			*//**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 *//*
			public static SendSMS parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				SendSMS object = new SendSMS();

				int event;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");
						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"sendSMS".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);
								return (SendSMS) ExtensionMapper.getTypeObject(
										nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://http.net.sms.wisdom.com",
									"UserName").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setUserName(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://http.net.sms.wisdom.com",
									"Password").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setPassword(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://http.net.sms.wisdom.com", "msgs")
									.equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if ("true".equals(nillableValue)
								|| "1".equals(nillableValue)) {
							object.setMsgs(null);
							reader.next();

							reader.next();

						} else {

							object.setMsgs(SmsMessages.Factory.parse(reader));

							reader.next();
						}
					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());

				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class SmsMessage implements
			org.apache.axis2.databinding.ADBBean {
		
		 * This type was generated from the piece of schema that had name =
		 * SmsMessage Namespace URI = http://messages.net.sms.wisdom.com/xsd
		 * Namespace Prefix = ns1
		 

		*//**
		 * field for Contents
		 *//*

		protected java.lang.String localContents;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localContentsTracker = false;

		public boolean isContentsSpecified() {
			return localContentsTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getContents() {
			return localContents;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Contents
		 *//*
		public void setContents(java.lang.String param) {
			localContentsTracker = true;

			this.localContents = param;

		}

		*//**
		 * field for Labels This was an Array!
		 *//*

		protected Label[] localLabels;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localLabelsTracker = false;

		public boolean isLabelsSpecified() {
			return localLabelsTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return Label[]
		 *//*
		public Label[] getLabels() {
			return localLabels;
		}

		*//**
		 * validate the array for Labels
		 *//*
		protected void validateLabels(Label[] param) {

		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Labels
		 *//*
		public void setLabels(Label[] param) {

			validateLabels(param);

			localLabelsTracker = true;

			this.localLabels = param;
		}

		*//**
		 * Auto generated add method for the array for convenience
		 * 
		 * @param param
		 *            Label
		 *//*
		public void addLabels(Label param) {
			if (localLabels == null) {
				localLabels = new Label[] {};
			}

			// update the setting tracker
			localLabelsTracker = true;

			java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil
					.toList(localLabels);
			list.add(param);
			this.localLabels = (Label[]) list.toArray(new Label[list.size()]);

		}

		*//**
		 * field for OrgCode
		 *//*

		protected java.lang.String localOrgCode;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localOrgCodeTracker = false;

		public boolean isOrgCodeSpecified() {
			return localOrgCodeTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getOrgCode() {
			return localOrgCode;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            OrgCode
		 *//*
		public void setOrgCode(java.lang.String param) {
			localOrgCodeTracker = true;

			this.localOrgCode = param;

		}

		*//**
		 * field for Receiver
		 *//*

		protected java.lang.String localReceiver;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localReceiverTracker = false;

		public boolean isReceiverSpecified() {
			return localReceiverTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getReceiver() {
			return localReceiver;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Receiver
		 *//*
		public void setReceiver(java.lang.String param) {
			localReceiverTracker = true;

			this.localReceiver = param;

		}

		*//**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 *//*
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {

			org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
					this, parentQName);
			return factory.createOMElement(dataSource, parentQName);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {

			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"http://messages.net.sms.wisdom.com/xsd");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":SmsMessage", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "SmsMessage", xmlWriter);
				}

			}
			if (localContentsTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "contents", xmlWriter);

				if (localContents == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localContents);

				}

				xmlWriter.writeEndElement();
			}
			if (localLabelsTracker) {
				if (localLabels != null) {
					for (int i = 0; i < localLabels.length; i++) {
						if (localLabels[i] != null) {
							localLabels[i]
									.serialize(
											new javax.xml.namespace.QName(
													"http://messages.net.sms.wisdom.com/xsd",
													"labels"), xmlWriter);
						} else {

							writeStartElement(null,
									"http://messages.net.sms.wisdom.com/xsd",
									"labels", xmlWriter);

							// write the nil attribute
							writeAttribute(
									"xsi",
									"http://www.w3.org/2001/XMLSchema-instance",
									"nil", "1", xmlWriter);
							xmlWriter.writeEndElement();

						}

					}
				} else {

					writeStartElement(null,
							"http://messages.net.sms.wisdom.com/xsd", "labels",
							xmlWriter);

					// write the nil attribute
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);
					xmlWriter.writeEndElement();

				}
			}
			if (localOrgCodeTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "orgCode", xmlWriter);

				if (localOrgCode == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localOrgCode);

				}

				xmlWriter.writeEndElement();
			}
			if (localReceiverTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "receiver", xmlWriter);

				if (localReceiver == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localReceiver);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();

		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("http://messages.net.sms.wisdom.com/xsd")) {
				return "ns1";
			}
			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		*//**
		 * Utility method to write an element start tag.
		 *//*
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		*//**
		 * Util method to write an attribute with the ns prefix
		 *//*
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			java.lang.String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		*//**
		 * method to handle Qnames
		 *//*

		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}

			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		*//**
		 * Register a namespace prefix
		 *//*
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		*//**
		 * databinding method to get an XML representation of this object
		 * 
		 *//*
		public javax.xml.stream.XMLStreamReader getPullParser(
				javax.xml.namespace.QName qName)
				throws org.apache.axis2.databinding.ADBException {

			java.util.ArrayList elementList = new java.util.ArrayList();
			java.util.ArrayList attribList = new java.util.ArrayList();

			if (localContentsTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "contents"));

				elementList.add(localContents == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localContents));
			}
			if (localLabelsTracker) {
				if (localLabels != null) {
					for (int i = 0; i < localLabels.length; i++) {

						if (localLabels[i] != null) {
							elementList.add(new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"labels"));
							elementList.add(localLabels[i]);
						} else {

							elementList.add(new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"labels"));
							elementList.add(null);

						}

					}
				} else {

					elementList
							.add(new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"labels"));
					elementList.add(localLabels);

				}

			}
			if (localOrgCodeTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "orgCode"));

				elementList.add(localOrgCode == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localOrgCode));
			}
			if (localReceiverTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "receiver"));

				elementList.add(localReceiver == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localReceiver));
			}

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(
					qName, elementList.toArray(), attribList.toArray());

		}

		*//**
		 * Factory class that keeps the parse method
		 *//*
		public static class Factory {

			*//**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 *//*
			public static SmsMessage parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				SmsMessage object = new SmsMessage();

				int event;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");
						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"SmsMessage".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);
								return (SmsMessage) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					java.util.ArrayList list2 = new java.util.ArrayList();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"contents").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setContents(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"labels").equals(reader.getName())) {

						// Process the array and step past its final element's
						// end.

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if ("true".equals(nillableValue)
								|| "1".equals(nillableValue)) {
							list2.add(null);
							reader.next();
						} else {
							list2.add(Label.Factory.parse(reader));
						}
						// loop until we find a start element that is not part
						// of this array
						boolean loopDone2 = false;
						while (!loopDone2) {
							// We should be at the end element, but make sure
							while (!reader.isEndElement())
								reader.next();
							// Step out of this element
							reader.next();
							// Step to next element event.
							while (!reader.isStartElement()
									&& !reader.isEndElement())
								reader.next();
							if (reader.isEndElement()) {
								// two continuous end elements means we are
								// exiting the xml structure
								loopDone2 = true;
							} else {
								if (new javax.xml.namespace.QName(
										"http://messages.net.sms.wisdom.com/xsd",
										"labels").equals(reader.getName())) {

									nillableValue = reader
											.getAttributeValue(
													"http://www.w3.org/2001/XMLSchema-instance",
													"nil");
									if ("true".equals(nillableValue)
											|| "1".equals(nillableValue)) {
										list2.add(null);
										reader.next();
									} else {
										list2.add(Label.Factory.parse(reader));
									}
								} else {
									loopDone2 = true;
								}
							}
						}
						// call the converter utility to convert and set the
						// array

						object.setLabels((Label[]) org.apache.axis2.databinding.utils.ConverterUtil
								.convertToArray(Label.class, list2));

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"orgCode").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setOrgCode(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"receiver").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setReceiver(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());

				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class TXInsuranceExtension implements
			org.apache.axis2.databinding.ADBBean {
		
		 * This type was generated from the piece of schema that had name =
		 * TXInsuranceExtension Namespace URI =
		 * http://messages.net.sms.wisdom.com/xsd Namespace Prefix = ns1
		 

		*//**
		 * field for Operator
		 *//*

		protected java.lang.String localOperator;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localOperatorTracker = false;

		public boolean isOperatorSpecified() {
			return localOperatorTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getOperator() {
			return localOperator;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Operator
		 *//*
		public void setOperator(java.lang.String param) {
			localOperatorTracker = true;

			this.localOperator = param;

		}

		*//**
		 * field for OperatorKey
		 *//*

		protected java.lang.String localOperatorKey;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localOperatorKeyTracker = false;

		public boolean isOperatorKeySpecified() {
			return localOperatorKeyTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getOperatorKey() {
			return localOperatorKey;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            OperatorKey
		 *//*
		public void setOperatorKey(java.lang.String param) {
			localOperatorKeyTracker = true;

			this.localOperatorKey = param;

		}

		*//**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 *//*
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {

			org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
					this, parentQName);
			return factory.createOMElement(dataSource, parentQName);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {

			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"http://messages.net.sms.wisdom.com/xsd");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":TXInsuranceExtension",
							xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "TXInsuranceExtension", xmlWriter);
				}

			}
			if (localOperatorTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "operator", xmlWriter);

				if (localOperator == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localOperator);

				}

				xmlWriter.writeEndElement();
			}
			if (localOperatorKeyTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "operatorKey", xmlWriter);

				if (localOperatorKey == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localOperatorKey);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();

		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("http://messages.net.sms.wisdom.com/xsd")) {
				return "ns1";
			}
			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		*//**
		 * Utility method to write an element start tag.
		 *//*
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		*//**
		 * Util method to write an attribute with the ns prefix
		 *//*
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			java.lang.String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		*//**
		 * method to handle Qnames
		 *//*

		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}

			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		*//**
		 * Register a namespace prefix
		 *//*
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		*//**
		 * databinding method to get an XML representation of this object
		 * 
		 *//*
		public javax.xml.stream.XMLStreamReader getPullParser(
				javax.xml.namespace.QName qName)
				throws org.apache.axis2.databinding.ADBException {

			java.util.ArrayList elementList = new java.util.ArrayList();
			java.util.ArrayList attribList = new java.util.ArrayList();

			if (localOperatorTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "operator"));

				elementList.add(localOperator == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localOperator));
			}
			if (localOperatorKeyTracker) {
				elementList
						.add(new javax.xml.namespace.QName(
								"http://messages.net.sms.wisdom.com/xsd",
								"operatorKey"));

				elementList.add(localOperatorKey == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localOperatorKey));
			}

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(
					qName, elementList.toArray(), attribList.toArray());

		}

		*//**
		 * Factory class that keeps the parse method
		 *//*
		public static class Factory {

			*//**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 *//*
			public static TXInsuranceExtension parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				TXInsuranceExtension object = new TXInsuranceExtension();

				int event;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");
						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"TXInsuranceExtension".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);
								return (TXInsuranceExtension) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"operator").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setOperator(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"operatorKey").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setOperatorKey(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());

				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class SmsMessages implements
			org.apache.axis2.databinding.ADBBean {
		
		 * This type was generated from the piece of schema that had name =
		 * SmsMessages Namespace URI = http://messages.net.sms.wisdom.com/xsd
		 * Namespace Prefix = ns1
		 

		*//**
		 * field for TXInsuranceRequest
		 *//*

		protected TXInsuranceRequest localTXInsuranceRequest;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localTXInsuranceRequestTracker = false;

		public boolean isTXInsuranceRequestSpecified() {
			return localTXInsuranceRequestTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return TXInsuranceRequest
		 *//*
		public TXInsuranceRequest getTXInsuranceRequest() {
			return localTXInsuranceRequest;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TXInsuranceRequest
		 *//*
		public void setTXInsuranceRequest(TXInsuranceRequest param) {
			localTXInsuranceRequestTracker = true;

			this.localTXInsuranceRequest = param;

		}

		*//**
		 * field for TXInsuranceRequestExtension
		 *//*

		protected TXInsuranceRequestExtension localTXInsuranceRequestExtension;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localTXInsuranceRequestExtensionTracker = false;

		public boolean isTXInsuranceRequestExtensionSpecified() {
			return localTXInsuranceRequestExtensionTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return TXInsuranceRequestExtension
		 *//*
		public TXInsuranceRequestExtension getTXInsuranceRequestExtension() {
			return localTXInsuranceRequestExtension;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TXInsuranceRequestExtension
		 *//*
		public void setTXInsuranceRequestExtension(
				TXInsuranceRequestExtension param) {
			localTXInsuranceRequestExtensionTracker = true;

			this.localTXInsuranceRequestExtension = param;

		}

		*//**
		 * field for Access
		 *//*

		protected java.lang.String localAccess;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localAccessTracker = false;

		public boolean isAccessSpecified() {
			return localAccessTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getAccess() {
			return localAccess;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Access
		 *//*
		public void setAccess(java.lang.String param) {
			localAccessTracker = true;

			this.localAccess = param;

		}

		*//**
		 * field for EndDate
		 *//*

		protected java.lang.String localEndDate;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localEndDateTracker = false;

		public boolean isEndDateSpecified() {
			return localEndDateTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getEndDate() {
			return localEndDate;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            EndDate
		 *//*
		public void setEndDate(java.lang.String param) {
			localEndDateTracker = true;

			this.localEndDate = param;

		}

		*//**
		 * field for EndTime
		 *//*

		protected java.lang.String localEndTime;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localEndTimeTracker = false;

		public boolean isEndTimeSpecified() {
			return localEndTimeTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getEndTime() {
			return localEndTime;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            EndTime
		 *//*
		public void setEndTime(java.lang.String param) {
			localEndTimeTracker = true;

			this.localEndTime = param;

		}

		*//**
		 * field for Extension
		 *//*

		protected java.lang.String localExtension;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localExtensionTracker = false;

		public boolean isExtensionSpecified() {
			return localExtensionTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getExtension() {
			return localExtension;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Extension
		 *//*
		public void setExtension(java.lang.String param) {
			localExtensionTracker = true;

			this.localExtension = param;

		}

		*//**
		 * field for ExtensionValue
		 *//*

		protected java.lang.String localExtensionValue;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localExtensionValueTracker = false;

		public boolean isExtensionValueSpecified() {
			return localExtensionValueTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getExtensionValue() {
			return localExtensionValue;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            ExtensionValue
		 *//*
		public void setExtensionValue(java.lang.String param) {
			localExtensionValueTracker = true;

			this.localExtensionValue = param;

		}

		*//**
		 * field for HashMap
		 *//*

		protected java.lang.Object localHashMap;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localHashMapTracker = false;

		public boolean isHashMapSpecified() {
			return localHashMapTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.Object
		 *//*
		public java.lang.Object getHashMap() {
			return localHashMap;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            HashMap
		 *//*
		public void setHashMap(java.lang.Object param) {
			localHashMapTracker = true;

			this.localHashMap = param;

		}

		*//**
		 * field for HashMapExten
		 *//*

		protected java.lang.Object localHashMapExten;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localHashMapExtenTracker = false;

		public boolean isHashMapExtenSpecified() {
			return localHashMapExtenTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.Object
		 *//*
		public java.lang.Object getHashMapExten() {
			return localHashMapExten;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            HashMapExten
		 *//*
		public void setHashMapExten(java.lang.Object param) {
			localHashMapExtenTracker = true;

			this.localHashMapExten = param;

		}

		*//**
		 * field for Messages This was an Array!
		 *//*

		protected SmsMessage[] localMessages;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localMessagesTracker = false;

		public boolean isMessagesSpecified() {
			return localMessagesTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return SmsMessage[]
		 *//*
		public SmsMessage[] getMessages() {
			return localMessages;
		}

		*//**
		 * validate the array for Messages
		 *//*
		protected void validateMessages(SmsMessage[] param) {

		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Messages
		 *//*
		public void setMessages(SmsMessage[] param) {

			validateMessages(param);

			localMessagesTracker = true;

			this.localMessages = param;
		}

		*//**
		 * Auto generated add method for the array for convenience
		 * 
		 * @param param
		 *            SmsMessage
		 *//*
		public void addMessages(SmsMessage param) {
			if (localMessages == null) {
				localMessages = new SmsMessage[] {};
			}

			// update the setting tracker
			localMessagesTracker = true;

			java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil
					.toList(localMessages);
			list.add(param);
			this.localMessages = (SmsMessage[]) list
					.toArray(new SmsMessage[list.size()]);

		}

		*//**
		 * field for NeedUseTemplateFlag
		 *//*

		protected java.lang.String localNeedUseTemplateFlag;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localNeedUseTemplateFlagTracker = false;

		public boolean isNeedUseTemplateFlagSpecified() {
			return localNeedUseTemplateFlagTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getNeedUseTemplateFlag() {
			return localNeedUseTemplateFlag;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            NeedUseTemplateFlag
		 *//*
		public void setNeedUseTemplateFlag(java.lang.String param) {
			localNeedUseTemplateFlagTracker = true;

			this.localNeedUseTemplateFlag = param;

		}

		*//**
		 * field for OrganizationId
		 *//*

		protected java.lang.String localOrganizationId;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localOrganizationIdTracker = false;

		public boolean isOrganizationIdSpecified() {
			return localOrganizationIdTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getOrganizationId() {
			return localOrganizationId;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            OrganizationId
		 *//*
		public void setOrganizationId(java.lang.String param) {
			localOrganizationIdTracker = true;

			this.localOrganizationId = param;

		}

		*//**
		 * field for ServiceType
		 *//*

		protected java.lang.String localServiceType;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localServiceTypeTracker = false;

		public boolean isServiceTypeSpecified() {
			return localServiceTypeTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getServiceType() {
			return localServiceType;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            ServiceType
		 *//*
		public void setServiceType(java.lang.String param) {
			localServiceTypeTracker = true;

			this.localServiceType = param;

		}

		*//**
		 * field for SmTaskID
		 *//*

		protected java.lang.String localSmTaskID;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localSmTaskIDTracker = false;

		public boolean isSmTaskIDSpecified() {
			return localSmTaskIDTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getSmTaskID() {
			return localSmTaskID;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            SmTaskID
		 *//*
		public void setSmTaskID(java.lang.String param) {
			localSmTaskIDTracker = true;

			this.localSmTaskID = param;

		}

		*//**
		 * field for StartDate
		 *//*

		protected java.lang.String localStartDate;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localStartDateTracker = false;

		public boolean isStartDateSpecified() {
			return localStartDateTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getStartDate() {
			return localStartDate;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            StartDate
		 *//*
		public void setStartDate(java.lang.String param) {
			localStartDateTracker = true;

			this.localStartDate = param;

		}

		*//**
		 * field for StartTime
		 *//*

		protected java.lang.String localStartTime;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localStartTimeTracker = false;

		public boolean isStartTimeSpecified() {
			return localStartTimeTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getStartTime() {
			return localStartTime;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            StartTime
		 *//*
		public void setStartTime(java.lang.String param) {
			localStartTimeTracker = true;

			this.localStartTime = param;

		}

		*//**
		 * field for TaskId
		 *//*

		protected java.lang.String localTaskId;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localTaskIdTracker = false;

		public boolean isTaskIdSpecified() {
			return localTaskIdTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getTaskId() {
			return localTaskId;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TaskId
		 *//*
		public void setTaskId(java.lang.String param) {
			localTaskIdTracker = true;

			this.localTaskId = param;

		}

		*//**
		 * field for TaskValue
		 *//*

		protected java.lang.String localTaskValue;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localTaskValueTracker = false;

		public boolean isTaskValueSpecified() {
			return localTaskValueTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getTaskValue() {
			return localTaskValue;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TaskValue
		 *//*
		public void setTaskValue(java.lang.String param) {
			localTaskValueTracker = true;

			this.localTaskValue = param;

		}

		*//**
		 * field for TemplatId
		 *//*

		protected java.lang.String localTemplatId;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localTemplatIdTracker = false;

		public boolean isTemplatIdSpecified() {
			return localTemplatIdTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getTemplatId() {
			return localTemplatId;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TemplatId
		 *//*
		public void setTemplatId(java.lang.String param) {
			localTemplatIdTracker = true;

			this.localTemplatId = param;

		}

		*//**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 *//*
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {

			org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
					this, parentQName);
			return factory.createOMElement(dataSource, parentQName);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {

			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"http://messages.net.sms.wisdom.com/xsd");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":SmsMessages", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "SmsMessages", xmlWriter);
				}

			}
			if (localTXInsuranceRequestTracker) {
				if (localTXInsuranceRequest == null) {

					writeStartElement(null,
							"http://messages.net.sms.wisdom.com/xsd",
							"TXInsuranceRequest", xmlWriter);

					// write the nil attribute
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);
					xmlWriter.writeEndElement();
				} else {
					localTXInsuranceRequest.serialize(
							new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"TXInsuranceRequest"), xmlWriter);
				}
			}
			if (localTXInsuranceRequestExtensionTracker) {
				if (localTXInsuranceRequestExtension == null) {

					writeStartElement(null,
							"http://messages.net.sms.wisdom.com/xsd",
							"TXInsuranceRequestExtension", xmlWriter);

					// write the nil attribute
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);
					xmlWriter.writeEndElement();
				} else {
					localTXInsuranceRequestExtension.serialize(
							new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"TXInsuranceRequestExtension"), xmlWriter);
				}
			}
			if (localAccessTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "access", xmlWriter);

				if (localAccess == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localAccess);

				}

				xmlWriter.writeEndElement();
			}
			if (localEndDateTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "endDate", xmlWriter);

				if (localEndDate == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localEndDate);

				}

				xmlWriter.writeEndElement();
			}
			if (localEndTimeTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "endTime", xmlWriter);

				if (localEndTime == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localEndTime);

				}

				xmlWriter.writeEndElement();
			}
			if (localExtensionTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "extension", xmlWriter);

				if (localExtension == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localExtension);

				}

				xmlWriter.writeEndElement();
			}
			if (localExtensionValueTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "extensionValue", xmlWriter);

				if (localExtensionValue == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localExtensionValue);

				}

				xmlWriter.writeEndElement();
			}
			if (localHashMapTracker) {

				if (localHashMap != null) {
					if (localHashMap instanceof org.apache.axis2.databinding.ADBBean) {
						((org.apache.axis2.databinding.ADBBean) localHashMap)
								.serialize(
										new javax.xml.namespace.QName(
												"http://messages.net.sms.wisdom.com/xsd",
												"hashMap"), xmlWriter, true);
					} else {
						writeStartElement(null,
								"http://messages.net.sms.wisdom.com/xsd",
								"hashMap", xmlWriter);
						org.apache.axis2.databinding.utils.ConverterUtil
								.serializeAnyType(localHashMap, xmlWriter);
						xmlWriter.writeEndElement();
					}
				} else {

					// write null attribute
					writeStartElement(null,
							"http://messages.net.sms.wisdom.com/xsd",
							"hashMap", xmlWriter);

					// write the nil attribute
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);
					xmlWriter.writeEndElement();

				}

			}
			if (localHashMapExtenTracker) {

				if (localHashMapExten != null) {
					if (localHashMapExten instanceof org.apache.axis2.databinding.ADBBean) {
						((org.apache.axis2.databinding.ADBBean) localHashMapExten)
								.serialize(
										new javax.xml.namespace.QName(
												"http://messages.net.sms.wisdom.com/xsd",
												"hashMapExten"), xmlWriter,
										true);
					} else {
						writeStartElement(null,
								"http://messages.net.sms.wisdom.com/xsd",
								"hashMapExten", xmlWriter);
						org.apache.axis2.databinding.utils.ConverterUtil
								.serializeAnyType(localHashMapExten, xmlWriter);
						xmlWriter.writeEndElement();
					}
				} else {

					// write null attribute
					writeStartElement(null,
							"http://messages.net.sms.wisdom.com/xsd",
							"hashMapExten", xmlWriter);

					// write the nil attribute
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);
					xmlWriter.writeEndElement();

				}

			}
			if (localMessagesTracker) {
				if (localMessages != null) {
					for (int i = 0; i < localMessages.length; i++) {
						if (localMessages[i] != null) {
							localMessages[i]
									.serialize(
											new javax.xml.namespace.QName(
													"http://messages.net.sms.wisdom.com/xsd",
													"messages"), xmlWriter);
						} else {

							writeStartElement(null,
									"http://messages.net.sms.wisdom.com/xsd",
									"messages", xmlWriter);

							// write the nil attribute
							writeAttribute(
									"xsi",
									"http://www.w3.org/2001/XMLSchema-instance",
									"nil", "1", xmlWriter);
							xmlWriter.writeEndElement();

						}

					}
				} else {

					writeStartElement(null,
							"http://messages.net.sms.wisdom.com/xsd",
							"messages", xmlWriter);

					// write the nil attribute
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);
					xmlWriter.writeEndElement();

				}
			}
			if (localNeedUseTemplateFlagTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "needUseTemplateFlag",
						xmlWriter);

				if (localNeedUseTemplateFlag == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localNeedUseTemplateFlag);

				}

				xmlWriter.writeEndElement();
			}
			if (localOrganizationIdTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "organizationId", xmlWriter);

				if (localOrganizationId == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localOrganizationId);

				}

				xmlWriter.writeEndElement();
			}
			if (localServiceTypeTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "serviceType", xmlWriter);

				if (localServiceType == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localServiceType);

				}

				xmlWriter.writeEndElement();
			}
			if (localSmTaskIDTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "smTaskID", xmlWriter);

				if (localSmTaskID == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localSmTaskID);

				}

				xmlWriter.writeEndElement();
			}
			if (localStartDateTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "startDate", xmlWriter);

				if (localStartDate == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localStartDate);

				}

				xmlWriter.writeEndElement();
			}
			if (localStartTimeTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "startTime", xmlWriter);

				if (localStartTime == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localStartTime);

				}

				xmlWriter.writeEndElement();
			}
			if (localTaskIdTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "taskId", xmlWriter);

				if (localTaskId == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localTaskId);

				}

				xmlWriter.writeEndElement();
			}
			if (localTaskValueTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "taskValue", xmlWriter);

				if (localTaskValue == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localTaskValue);

				}

				xmlWriter.writeEndElement();
			}
			if (localTemplatIdTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "templatId", xmlWriter);

				if (localTemplatId == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localTemplatId);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();

		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("http://messages.net.sms.wisdom.com/xsd")) {
				return "ns1";
			}
			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		*//**
		 * Utility method to write an element start tag.
		 *//*
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		*//**
		 * Util method to write an attribute with the ns prefix
		 *//*
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			java.lang.String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		*//**
		 * method to handle Qnames
		 *//*

		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}

			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		*//**
		 * Register a namespace prefix
		 *//*
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		*//**
		 * databinding method to get an XML representation of this object
		 * 
		 *//*
		public javax.xml.stream.XMLStreamReader getPullParser(
				javax.xml.namespace.QName qName)
				throws org.apache.axis2.databinding.ADBException {

			java.util.ArrayList elementList = new java.util.ArrayList();
			java.util.ArrayList attribList = new java.util.ArrayList();

			if (localTXInsuranceRequestTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd",
						"TXInsuranceRequest"));

				elementList.add(localTXInsuranceRequest == null ? null
						: localTXInsuranceRequest);
			}
			if (localTXInsuranceRequestExtensionTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd",
						"TXInsuranceRequestExtension"));

				elementList.add(localTXInsuranceRequestExtension == null ? null
						: localTXInsuranceRequestExtension);
			}
			if (localAccessTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "access"));

				elementList.add(localAccess == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localAccess));
			}
			if (localEndDateTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "endDate"));

				elementList.add(localEndDate == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localEndDate));
			}
			if (localEndTimeTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "endTime"));

				elementList.add(localEndTime == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localEndTime));
			}
			if (localExtensionTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "extension"));

				elementList.add(localExtension == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localExtension));
			}
			if (localExtensionValueTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd",
						"extensionValue"));

				elementList.add(localExtensionValue == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localExtensionValue));
			}
			if (localHashMapTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "hashMap"));

				elementList.add(localHashMap == null ? null : localHashMap);
			}
			if (localHashMapExtenTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd",
						"hashMapExten"));

				elementList.add(localHashMapExten == null ? null
						: localHashMapExten);
			}
			if (localMessagesTracker) {
				if (localMessages != null) {
					for (int i = 0; i < localMessages.length; i++) {

						if (localMessages[i] != null) {
							elementList.add(new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"messages"));
							elementList.add(localMessages[i]);
						} else {

							elementList.add(new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"messages"));
							elementList.add(null);

						}

					}
				} else {

					elementList.add(new javax.xml.namespace.QName(
							"http://messages.net.sms.wisdom.com/xsd",
							"messages"));
					elementList.add(localMessages);

				}

			}
			if (localNeedUseTemplateFlagTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd",
						"needUseTemplateFlag"));

				elementList.add(localNeedUseTemplateFlag == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localNeedUseTemplateFlag));
			}
			if (localOrganizationIdTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd",
						"organizationId"));

				elementList.add(localOrganizationId == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localOrganizationId));
			}
			if (localServiceTypeTracker) {
				elementList
						.add(new javax.xml.namespace.QName(
								"http://messages.net.sms.wisdom.com/xsd",
								"serviceType"));

				elementList.add(localServiceType == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localServiceType));
			}
			if (localSmTaskIDTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "smTaskID"));

				elementList.add(localSmTaskID == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localSmTaskID));
			}
			if (localStartDateTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "startDate"));

				elementList.add(localStartDate == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localStartDate));
			}
			if (localStartTimeTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "startTime"));

				elementList.add(localStartTime == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localStartTime));
			}
			if (localTaskIdTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "taskId"));

				elementList.add(localTaskId == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localTaskId));
			}
			if (localTaskValueTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "taskValue"));

				elementList.add(localTaskValue == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localTaskValue));
			}
			if (localTemplatIdTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "templatId"));

				elementList.add(localTemplatId == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localTemplatId));
			}

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(
					qName, elementList.toArray(), attribList.toArray());

		}

		*//**
		 * Factory class that keeps the parse method
		 *//*
		public static class Factory {

			*//**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 *//*
			public static SmsMessages parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				SmsMessages object = new SmsMessages();

				int event;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");
						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"SmsMessages".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);
								return (SmsMessages) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					java.util.ArrayList list10 = new java.util.ArrayList();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"TXInsuranceRequest").equals(reader
									.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if ("true".equals(nillableValue)
								|| "1".equals(nillableValue)) {
							object.setTXInsuranceRequest(null);
							reader.next();

							reader.next();

						} else {

							object.setTXInsuranceRequest(TXInsuranceRequest.Factory
									.parse(reader));

							reader.next();
						}
					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"TXInsuranceRequestExtension")
									.equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if ("true".equals(nillableValue)
								|| "1".equals(nillableValue)) {
							object.setTXInsuranceRequestExtension(null);
							reader.next();

							reader.next();

						} else {

							object.setTXInsuranceRequestExtension(TXInsuranceRequestExtension.Factory
									.parse(reader));

							reader.next();
						}
					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"access").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setAccess(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"endDate").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setEndDate(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"endTime").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setEndTime(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"extension").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setExtension(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"extensionValue").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setExtensionValue(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"hashMap").equals(reader.getName())) {

						object.setHashMap(org.apache.axis2.databinding.utils.ConverterUtil
								.getAnyTypeObject(reader, ExtensionMapper.class));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"hashMapExten").equals(reader.getName())) {

						object.setHashMapExten(org.apache.axis2.databinding.utils.ConverterUtil
								.getAnyTypeObject(reader, ExtensionMapper.class));

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"messages").equals(reader.getName())) {

						// Process the array and step past its final element's
						// end.

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if ("true".equals(nillableValue)
								|| "1".equals(nillableValue)) {
							list10.add(null);
							reader.next();
						} else {
							list10.add(SmsMessage.Factory.parse(reader));
						}
						// loop until we find a start element that is not part
						// of this array
						boolean loopDone10 = false;
						while (!loopDone10) {
							// We should be at the end element, but make sure
							while (!reader.isEndElement())
								reader.next();
							// Step out of this element
							reader.next();
							// Step to next element event.
							while (!reader.isStartElement()
									&& !reader.isEndElement())
								reader.next();
							if (reader.isEndElement()) {
								// two continuous end elements means we are
								// exiting the xml structure
								loopDone10 = true;
							} else {
								if (new javax.xml.namespace.QName(
										"http://messages.net.sms.wisdom.com/xsd",
										"messages").equals(reader.getName())) {

									nillableValue = reader
											.getAttributeValue(
													"http://www.w3.org/2001/XMLSchema-instance",
													"nil");
									if ("true".equals(nillableValue)
											|| "1".equals(nillableValue)) {
										list10.add(null);
										reader.next();
									} else {
										list10.add(SmsMessage.Factory
												.parse(reader));
									}
								} else {
									loopDone10 = true;
								}
							}
						}
						// call the converter utility to convert and set the
						// array

						object.setMessages((SmsMessage[]) org.apache.axis2.databinding.utils.ConverterUtil
								.convertToArray(SmsMessage.class, list10));

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"needUseTemplateFlag").equals(reader
									.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setNeedUseTemplateFlag(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"organizationId").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setOrganizationId(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"serviceType").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setServiceType(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"smTaskID").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setSmTaskID(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"startDate").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setStartDate(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"startTime").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setStartTime(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"taskId").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setTaskId(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"taskValue").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setTaskValue(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"templatId").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setTemplatId(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());

				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class TXInsurance implements
			org.apache.axis2.databinding.ADBBean {
		
		 * This type was generated from the piece of schema that had name =
		 * TXInsurance Namespace URI = http://messages.net.sms.wisdom.com/xsd
		 * Namespace Prefix = ns1
		 

		*//**
		 * field for TransExeDate
		 *//*

		protected java.lang.String localTransExeDate;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localTransExeDateTracker = false;

		public boolean isTransExeDateSpecified() {
			return localTransExeDateTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getTransExeDate() {
			return localTransExeDate;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TransExeDate
		 *//*
		public void setTransExeDate(java.lang.String param) {
			localTransExeDateTracker = true;

			this.localTransExeDate = param;

		}

		*//**
		 * field for TransExeTime
		 *//*

		protected java.lang.String localTransExeTime;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localTransExeTimeTracker = false;

		public boolean isTransExeTimeSpecified() {
			return localTransExeTimeTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getTransExeTime() {
			return localTransExeTime;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TransExeTime
		 *//*
		public void setTransExeTime(java.lang.String param) {
			localTransExeTimeTracker = true;

			this.localTransExeTime = param;

		}

		*//**
		 * field for TransRefGUID
		 *//*

		protected java.lang.String localTransRefGUID;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localTransRefGUIDTracker = false;

		public boolean isTransRefGUIDSpecified() {
			return localTransRefGUIDTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getTransRefGUID() {
			return localTransRefGUID;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TransRefGUID
		 *//*
		public void setTransRefGUID(java.lang.String param) {
			localTransRefGUIDTracker = true;

			this.localTransRefGUID = param;

		}

		*//**
		 * field for TransSubType
		 *//*

		protected java.lang.String localTransSubType;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localTransSubTypeTracker = false;

		public boolean isTransSubTypeSpecified() {
			return localTransSubTypeTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getTransSubType() {
			return localTransSubType;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TransSubType
		 *//*
		public void setTransSubType(java.lang.String param) {
			localTransSubTypeTracker = true;

			this.localTransSubType = param;

		}

		*//**
		 * field for TransType
		 *//*

		protected java.lang.String localTransType;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localTransTypeTracker = false;

		public boolean isTransTypeSpecified() {
			return localTransTypeTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getTransType() {
			return localTransType;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TransType
		 *//*
		public void setTransType(java.lang.String param) {
			localTransTypeTracker = true;

			this.localTransType = param;

		}

		*//**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 *//*
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {

			org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
					this, parentQName);
			return factory.createOMElement(dataSource, parentQName);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {

			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"http://messages.net.sms.wisdom.com/xsd");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":TXInsurance", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "TXInsurance", xmlWriter);
				}

			}
			if (localTransExeDateTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "transExeDate", xmlWriter);

				if (localTransExeDate == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localTransExeDate);

				}

				xmlWriter.writeEndElement();
			}
			if (localTransExeTimeTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "transExeTime", xmlWriter);

				if (localTransExeTime == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localTransExeTime);

				}

				xmlWriter.writeEndElement();
			}
			if (localTransRefGUIDTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "transRefGUID", xmlWriter);

				if (localTransRefGUID == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localTransRefGUID);

				}

				xmlWriter.writeEndElement();
			}
			if (localTransSubTypeTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "transSubType", xmlWriter);

				if (localTransSubType == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localTransSubType);

				}

				xmlWriter.writeEndElement();
			}
			if (localTransTypeTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "transType", xmlWriter);

				if (localTransType == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localTransType);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();

		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("http://messages.net.sms.wisdom.com/xsd")) {
				return "ns1";
			}
			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		*//**
		 * Utility method to write an element start tag.
		 *//*
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		*//**
		 * Util method to write an attribute with the ns prefix
		 *//*
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			java.lang.String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		*//**
		 * method to handle Qnames
		 *//*

		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}

			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		*//**
		 * Register a namespace prefix
		 *//*
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		*//**
		 * databinding method to get an XML representation of this object
		 * 
		 *//*
		public javax.xml.stream.XMLStreamReader getPullParser(
				javax.xml.namespace.QName qName)
				throws org.apache.axis2.databinding.ADBException {

			java.util.ArrayList elementList = new java.util.ArrayList();
			java.util.ArrayList attribList = new java.util.ArrayList();

			if (localTransExeDateTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd",
						"transExeDate"));

				elementList.add(localTransExeDate == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localTransExeDate));
			}
			if (localTransExeTimeTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd",
						"transExeTime"));

				elementList.add(localTransExeTime == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localTransExeTime));
			}
			if (localTransRefGUIDTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd",
						"transRefGUID"));

				elementList.add(localTransRefGUID == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localTransRefGUID));
			}
			if (localTransSubTypeTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd",
						"transSubType"));

				elementList.add(localTransSubType == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localTransSubType));
			}
			if (localTransTypeTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "transType"));

				elementList.add(localTransType == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localTransType));
			}

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(
					qName, elementList.toArray(), attribList.toArray());

		}

		*//**
		 * Factory class that keeps the parse method
		 *//*
		public static class Factory {

			*//**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 *//*
			public static TXInsurance parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				TXInsurance object = new TXInsurance();

				int event;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");
						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"TXInsurance".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);
								return (TXInsurance) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"transExeDate").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setTransExeDate(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"transExeTime").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setTransExeTime(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"transRefGUID").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setTransRefGUID(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"transSubType").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setTransSubType(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"transType").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setTransType(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());

				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class Response implements
			org.apache.axis2.databinding.ADBBean {
		
		 * This type was generated from the piece of schema that had name =
		 * Response Namespace URI = http://http.net.sms.wisdom.com/xsd Namespace
		 * Prefix = ns2
		 

		*//**
		 * field for Message
		 *//*

		protected java.lang.String localMessage;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localMessageTracker = false;

		public boolean isMessageSpecified() {
			return localMessageTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getMessage() {
			return localMessage;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Message
		 *//*
		public void setMessage(java.lang.String param) {
			localMessageTracker = true;

			this.localMessage = param;

		}

		*//**
		 * field for Status
		 *//*

		protected java.lang.String localStatus;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localStatusTracker = false;

		public boolean isStatusSpecified() {
			return localStatusTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getStatus() {
			return localStatus;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Status
		 *//*
		public void setStatus(java.lang.String param) {
			localStatusTracker = true;

			this.localStatus = param;

		}

		*//**
		 * field for TXInsuranceResponse
		 *//*

		protected TXInsuranceResponse localTXInsuranceResponse;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localTXInsuranceResponseTracker = false;

		public boolean isTXInsuranceResponseSpecified() {
			return localTXInsuranceResponseTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return TXInsuranceResponse
		 *//*
		public TXInsuranceResponse getTXInsuranceResponse() {
			return localTXInsuranceResponse;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TXInsuranceResponse
		 *//*
		public void setTXInsuranceResponse(TXInsuranceResponse param) {
			localTXInsuranceResponseTracker = true;

			this.localTXInsuranceResponse = param;

		}

		*//**
		 * field for TXInsuranceResponseExtension
		 *//*

		protected TXInsuranceResponseExtension localTXInsuranceResponseExtension;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localTXInsuranceResponseExtensionTracker = false;

		public boolean isTXInsuranceResponseExtensionSpecified() {
			return localTXInsuranceResponseExtensionTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return TXInsuranceResponseExtension
		 *//*
		public TXInsuranceResponseExtension getTXInsuranceResponseExtension() {
			return localTXInsuranceResponseExtension;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TXInsuranceResponseExtension
		 *//*
		public void setTXInsuranceResponseExtension(
				TXInsuranceResponseExtension param) {
			localTXInsuranceResponseExtensionTracker = true;

			this.localTXInsuranceResponseExtension = param;

		}

		*//**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 *//*
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {

			org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
					this, parentQName);
			return factory.createOMElement(dataSource, parentQName);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {

			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"http://http.net.sms.wisdom.com/xsd");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":Response", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "Response", xmlWriter);
				}

			}
			if (localMessageTracker) {
				namespace = "http://http.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "message", xmlWriter);

				if (localMessage == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localMessage);

				}

				xmlWriter.writeEndElement();
			}
			if (localStatusTracker) {
				namespace = "http://http.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "status", xmlWriter);

				if (localStatus == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localStatus);

				}

				xmlWriter.writeEndElement();
			}
			if (localTXInsuranceResponseTracker) {
				if (localTXInsuranceResponse == null) {

					writeStartElement(null,
							"http://http.net.sms.wisdom.com/xsd",
							"tXInsuranceResponse", xmlWriter);

					// write the nil attribute
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);
					xmlWriter.writeEndElement();
				} else {
					localTXInsuranceResponse.serialize(
							new javax.xml.namespace.QName(
									"http://http.net.sms.wisdom.com/xsd",
									"tXInsuranceResponse"), xmlWriter);
				}
			}
			if (localTXInsuranceResponseExtensionTracker) {
				if (localTXInsuranceResponseExtension == null) {

					writeStartElement(null,
							"http://http.net.sms.wisdom.com/xsd",
							"tXInsuranceResponseExtension", xmlWriter);

					// write the nil attribute
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);
					xmlWriter.writeEndElement();
				} else {
					localTXInsuranceResponseExtension.serialize(
							new javax.xml.namespace.QName(
									"http://http.net.sms.wisdom.com/xsd",
									"tXInsuranceResponseExtension"), xmlWriter);
				}
			}
			xmlWriter.writeEndElement();

		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("http://http.net.sms.wisdom.com/xsd")) {
				return "ns2";
			}
			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		*//**
		 * Utility method to write an element start tag.
		 *//*
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		*//**
		 * Util method to write an attribute with the ns prefix
		 *//*
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			java.lang.String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		*//**
		 * method to handle Qnames
		 *//*

		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}

			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		*//**
		 * Register a namespace prefix
		 *//*
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		*//**
		 * databinding method to get an XML representation of this object
		 * 
		 *//*
		public javax.xml.stream.XMLStreamReader getPullParser(
				javax.xml.namespace.QName qName)
				throws org.apache.axis2.databinding.ADBException {

			java.util.ArrayList elementList = new java.util.ArrayList();
			java.util.ArrayList attribList = new java.util.ArrayList();

			if (localMessageTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://http.net.sms.wisdom.com/xsd", "message"));

				elementList.add(localMessage == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localMessage));
			}
			if (localStatusTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://http.net.sms.wisdom.com/xsd", "status"));

				elementList.add(localStatus == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localStatus));
			}
			if (localTXInsuranceResponseTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://http.net.sms.wisdom.com/xsd",
						"tXInsuranceResponse"));

				elementList.add(localTXInsuranceResponse == null ? null
						: localTXInsuranceResponse);
			}
			if (localTXInsuranceResponseExtensionTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://http.net.sms.wisdom.com/xsd",
						"tXInsuranceResponseExtension"));

				elementList
						.add(localTXInsuranceResponseExtension == null ? null
								: localTXInsuranceResponseExtension);
			}

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(
					qName, elementList.toArray(), attribList.toArray());

		}

		*//**
		 * Factory class that keeps the parse method
		 *//*
		public static class Factory {

			*//**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 *//*
			public static Response parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Response object = new Response();

				int event;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");
						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"Response".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);
								return (Response) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://http.net.sms.wisdom.com/xsd",
									"message").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setMessage(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://http.net.sms.wisdom.com/xsd",
									"status").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setStatus(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://http.net.sms.wisdom.com/xsd",
									"tXInsuranceResponse").equals(reader
									.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if ("true".equals(nillableValue)
								|| "1".equals(nillableValue)) {
							object.setTXInsuranceResponse(null);
							reader.next();

							reader.next();

						} else {

							object.setTXInsuranceResponse(TXInsuranceResponse.Factory
									.parse(reader));

							reader.next();
						}
					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://http.net.sms.wisdom.com/xsd",
									"tXInsuranceResponseExtension")
									.equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if ("true".equals(nillableValue)
								|| "1".equals(nillableValue)) {
							object.setTXInsuranceResponseExtension(null);
							reader.next();

							reader.next();

						} else {

							object.setTXInsuranceResponseExtension(TXInsuranceResponseExtension.Factory
									.parse(reader));

							reader.next();
						}
					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());

				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class CreateActivitySpreadTaskResponse implements
			org.apache.axis2.databinding.ADBBean {

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://http.net.sms.wisdom.com",
				"createActivitySpreadTaskResponse", "ns3");

		*//**
		 * field for _return
		 *//*

		protected Response local_return;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean local_returnTracker = false;

		public boolean is_returnSpecified() {
			return local_returnTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return Response
		 *//*
		public Response get_return() {
			return local_return;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            _return
		 *//*
		public void set_return(Response param) {
			local_returnTracker = true;

			this.local_return = param;

		}

		*//**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 *//*
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {

			org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
					this, MY_QNAME);
			return factory.createOMElement(dataSource, MY_QNAME);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {

			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"http://http.net.sms.wisdom.com");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix
									+ ":createActivitySpreadTaskResponse",
							xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "createActivitySpreadTaskResponse",
							xmlWriter);
				}

			}
			if (local_returnTracker) {
				if (local_return == null) {

					writeStartElement(null, "http://http.net.sms.wisdom.com",
							"return", xmlWriter);

					// write the nil attribute
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);
					xmlWriter.writeEndElement();
				} else {
					local_return.serialize(new javax.xml.namespace.QName(
							"http://http.net.sms.wisdom.com", "return"),
							xmlWriter);
				}
			}
			xmlWriter.writeEndElement();

		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("http://http.net.sms.wisdom.com")) {
				return "ns3";
			}
			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		*//**
		 * Utility method to write an element start tag.
		 *//*
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		*//**
		 * Util method to write an attribute with the ns prefix
		 *//*
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			java.lang.String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		*//**
		 * method to handle Qnames
		 *//*

		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}

			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		*//**
		 * Register a namespace prefix
		 *//*
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		*//**
		 * databinding method to get an XML representation of this object
		 * 
		 *//*
		public javax.xml.stream.XMLStreamReader getPullParser(
				javax.xml.namespace.QName qName)
				throws org.apache.axis2.databinding.ADBException {

			java.util.ArrayList elementList = new java.util.ArrayList();
			java.util.ArrayList attribList = new java.util.ArrayList();

			if (local_returnTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://http.net.sms.wisdom.com", "return"));

				elementList.add(local_return == null ? null : local_return);
			}

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(
					qName, elementList.toArray(), attribList.toArray());

		}

		*//**
		 * Factory class that keeps the parse method
		 *//*
		public static class Factory {

			*//**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 *//*
			public static CreateActivitySpreadTaskResponse parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				CreateActivitySpreadTaskResponse object = new CreateActivitySpreadTaskResponse();

				int event;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");
						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"createActivitySpreadTaskResponse"
									.equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);
								return (CreateActivitySpreadTaskResponse) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://http.net.sms.wisdom.com", "return")
									.equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if ("true".equals(nillableValue)
								|| "1".equals(nillableValue)) {
							object.set_return(null);
							reader.next();

							reader.next();

						} else {

							object.set_return(Response.Factory.parse(reader));

							reader.next();
						}
					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());

				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class SpreadTask implements
			org.apache.axis2.databinding.ADBBean {
		
		 * This type was generated from the piece of schema that had name =
		 * SpreadTask Namespace URI = http://messages.net.sms.wisdom.com/xsd
		 * Namespace Prefix = ns1
		 

		*//**
		 * field for ActivityId
		 *//*

		protected java.lang.String localActivityId;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localActivityIdTracker = false;

		public boolean isActivityIdSpecified() {
			return localActivityIdTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getActivityId() {
			return localActivityId;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            ActivityId
		 *//*
		public void setActivityId(java.lang.String param) {
			localActivityIdTracker = true;

			this.localActivityId = param;

		}

		*//**
		 * field for EndDate
		 *//*

		protected java.lang.String localEndDate;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localEndDateTracker = false;

		public boolean isEndDateSpecified() {
			return localEndDateTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getEndDate() {
			return localEndDate;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            EndDate
		 *//*
		public void setEndDate(java.lang.String param) {
			localEndDateTracker = true;

			this.localEndDate = param;

		}

		*//**
		 * field for EndTime
		 *//*

		protected java.lang.String localEndTime;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localEndTimeTracker = false;

		public boolean isEndTimeSpecified() {
			return localEndTimeTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getEndTime() {
			return localEndTime;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            EndTime
		 *//*
		public void setEndTime(java.lang.String param) {
			localEndTimeTracker = true;

			this.localEndTime = param;

		}

		*//**
		 * field for OrganizationId
		 *//*

		protected java.lang.String localOrganizationId;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localOrganizationIdTracker = false;

		public boolean isOrganizationIdSpecified() {
			return localOrganizationIdTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getOrganizationId() {
			return localOrganizationId;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            OrganizationId
		 *//*
		public void setOrganizationId(java.lang.String param) {
			localOrganizationIdTracker = true;

			this.localOrganizationId = param;

		}

		*//**
		 * field for RelateBusinessInfo
		 *//*

		protected java.lang.String localRelateBusinessInfo;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localRelateBusinessInfoTracker = false;

		public boolean isRelateBusinessInfoSpecified() {
			return localRelateBusinessInfoTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getRelateBusinessInfo() {
			return localRelateBusinessInfo;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            RelateBusinessInfo
		 *//*
		public void setRelateBusinessInfo(java.lang.String param) {
			localRelateBusinessInfoTracker = true;

			this.localRelateBusinessInfo = param;

		}

		*//**
		 * field for StartDate
		 *//*

		protected java.lang.String localStartDate;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localStartDateTracker = false;

		public boolean isStartDateSpecified() {
			return localStartDateTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getStartDate() {
			return localStartDate;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            StartDate
		 *//*
		public void setStartDate(java.lang.String param) {
			localStartDateTracker = true;

			this.localStartDate = param;

		}

		*//**
		 * field for StartTime
		 *//*

		protected java.lang.String localStartTime;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localStartTimeTracker = false;

		public boolean isStartTimeSpecified() {
			return localStartTimeTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getStartTime() {
			return localStartTime;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            StartTime
		 *//*
		public void setStartTime(java.lang.String param) {
			localStartTimeTracker = true;

			this.localStartTime = param;

		}

		*//**
		 * field for TXInsuranceRequest
		 *//*

		protected TXInsuranceRequest localTXInsuranceRequest;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localTXInsuranceRequestTracker = false;

		public boolean isTXInsuranceRequestSpecified() {
			return localTXInsuranceRequestTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return TXInsuranceRequest
		 *//*
		public TXInsuranceRequest getTXInsuranceRequest() {
			return localTXInsuranceRequest;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TXInsuranceRequest
		 *//*
		public void setTXInsuranceRequest(TXInsuranceRequest param) {
			localTXInsuranceRequestTracker = true;

			this.localTXInsuranceRequest = param;

		}

		*//**
		 * field for TXInsuranceRequestExtension
		 *//*

		protected TXInsuranceRequestExtension localTXInsuranceRequestExtension;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localTXInsuranceRequestExtensionTracker = false;

		public boolean isTXInsuranceRequestExtensionSpecified() {
			return localTXInsuranceRequestExtensionTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return TXInsuranceRequestExtension
		 *//*
		public TXInsuranceRequestExtension getTXInsuranceRequestExtension() {
			return localTXInsuranceRequestExtension;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TXInsuranceRequestExtension
		 *//*
		public void setTXInsuranceRequestExtension(
				TXInsuranceRequestExtension param) {
			localTXInsuranceRequestExtensionTracker = true;

			this.localTXInsuranceRequestExtension = param;

		}

		*//**
		 * field for TaskId
		 *//*

		protected java.lang.String localTaskId;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localTaskIdTracker = false;

		public boolean isTaskIdSpecified() {
			return localTaskIdTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getTaskId() {
			return localTaskId;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TaskId
		 *//*
		public void setTaskId(java.lang.String param) {
			localTaskIdTracker = true;

			this.localTaskId = param;

		}

		*//**
		 * field for TaskTargets This was an Array!
		 *//*

		protected TaskTarget[] localTaskTargets;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localTaskTargetsTracker = false;

		public boolean isTaskTargetsSpecified() {
			return localTaskTargetsTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return TaskTarget[]
		 *//*
		public TaskTarget[] getTaskTargets() {
			return localTaskTargets;
		}

		*//**
		 * validate the array for TaskTargets
		 *//*
		protected void validateTaskTargets(TaskTarget[] param) {

		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TaskTargets
		 *//*
		public void setTaskTargets(TaskTarget[] param) {

			validateTaskTargets(param);

			localTaskTargetsTracker = true;

			this.localTaskTargets = param;
		}

		*//**
		 * Auto generated add method for the array for convenience
		 * 
		 * @param param
		 *            TaskTarget
		 *//*
		public void addTaskTargets(TaskTarget param) {
			if (localTaskTargets == null) {
				localTaskTargets = new TaskTarget[] {};
			}

			// update the setting tracker
			localTaskTargetsTracker = true;

			java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil
					.toList(localTaskTargets);
			list.add(param);
			this.localTaskTargets = (TaskTarget[]) list
					.toArray(new TaskTarget[list.size()]);

		}

		*//**
		 * field for TaskValue
		 *//*

		protected java.lang.String localTaskValue;

		
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 
		protected boolean localTaskValueTracker = false;

		public boolean isTaskValueSpecified() {
			return localTaskValueTracker;
		}

		*//**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 *//*
		public java.lang.String getTaskValue() {
			return localTaskValue;
		}

		*//**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TaskValue
		 *//*
		public void setTaskValue(java.lang.String param) {
			localTaskValueTracker = true;

			this.localTaskValue = param;

		}

		*//**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 *//*
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {

			org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
					this, parentQName);
			return factory.createOMElement(dataSource, parentQName);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {

			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {

				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"http://messages.net.sms.wisdom.com/xsd");
				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":SpreadTask", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "SpreadTask", xmlWriter);
				}

			}
			if (localActivityIdTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "activityId", xmlWriter);

				if (localActivityId == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localActivityId);

				}

				xmlWriter.writeEndElement();
			}
			if (localEndDateTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "endDate", xmlWriter);

				if (localEndDate == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localEndDate);

				}

				xmlWriter.writeEndElement();
			}
			if (localEndTimeTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "endTime", xmlWriter);

				if (localEndTime == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localEndTime);

				}

				xmlWriter.writeEndElement();
			}
			if (localOrganizationIdTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "organizationId", xmlWriter);

				if (localOrganizationId == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localOrganizationId);

				}

				xmlWriter.writeEndElement();
			}
			if (localRelateBusinessInfoTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "relateBusinessInfo",
						xmlWriter);

				if (localRelateBusinessInfo == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localRelateBusinessInfo);

				}

				xmlWriter.writeEndElement();
			}
			if (localStartDateTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "startDate", xmlWriter);

				if (localStartDate == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localStartDate);

				}

				xmlWriter.writeEndElement();
			}
			if (localStartTimeTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "startTime", xmlWriter);

				if (localStartTime == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localStartTime);

				}

				xmlWriter.writeEndElement();
			}
			if (localTXInsuranceRequestTracker) {
				if (localTXInsuranceRequest == null) {

					writeStartElement(null,
							"http://messages.net.sms.wisdom.com/xsd",
							"tXInsuranceRequest", xmlWriter);

					// write the nil attribute
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);
					xmlWriter.writeEndElement();
				} else {
					localTXInsuranceRequest.serialize(
							new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"tXInsuranceRequest"), xmlWriter);
				}
			}
			if (localTXInsuranceRequestExtensionTracker) {
				if (localTXInsuranceRequestExtension == null) {

					writeStartElement(null,
							"http://messages.net.sms.wisdom.com/xsd",
							"tXInsuranceRequestExtension", xmlWriter);

					// write the nil attribute
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);
					xmlWriter.writeEndElement();
				} else {
					localTXInsuranceRequestExtension.serialize(
							new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"tXInsuranceRequestExtension"), xmlWriter);
				}
			}
			if (localTaskIdTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "taskId", xmlWriter);

				if (localTaskId == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localTaskId);

				}

				xmlWriter.writeEndElement();
			}
			if (localTaskTargetsTracker) {
				if (localTaskTargets != null) {
					for (int i = 0; i < localTaskTargets.length; i++) {
						if (localTaskTargets[i] != null) {
							localTaskTargets[i]
									.serialize(
											new javax.xml.namespace.QName(
													"http://messages.net.sms.wisdom.com/xsd",
													"taskTargets"), xmlWriter);
						} else {

							writeStartElement(null,
									"http://messages.net.sms.wisdom.com/xsd",
									"taskTargets", xmlWriter);

							// write the nil attribute
							writeAttribute(
									"xsi",
									"http://www.w3.org/2001/XMLSchema-instance",
									"nil", "1", xmlWriter);
							xmlWriter.writeEndElement();

						}

					}
				} else {

					writeStartElement(null,
							"http://messages.net.sms.wisdom.com/xsd",
							"taskTargets", xmlWriter);

					// write the nil attribute
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);
					xmlWriter.writeEndElement();

				}
			}
			if (localTaskValueTracker) {
				namespace = "http://messages.net.sms.wisdom.com/xsd";
				writeStartElement(null, namespace, "taskValue", xmlWriter);

				if (localTaskValue == null) {
					// write the nil attribute

					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance", "nil",
							"1", xmlWriter);

				} else {

					xmlWriter.writeCharacters(localTaskValue);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();

		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("http://messages.net.sms.wisdom.com/xsd")) {
				return "ns1";
			}
			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		*//**
		 * Utility method to write an element start tag.
		 *//*
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
			if (writerPrefix != null) {
				xmlWriter.writeStartElement(namespace, localPart);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		*//**
		 * Util method to write an attribute with the ns prefix
		 *//*
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (xmlWriter.getPrefix(namespace) == null) {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attValue);
			}
		}

		*//**
		 * Util method to write an attribute without the ns prefix
		 *//*
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);
			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}
			java.lang.String attributeValue;
			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(namespace, attName, attributeValue);
			}
		}

		*//**
		 * method to handle Qnames
		 *//*

		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();
			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}

			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {

			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}
					namespaceURI = qnames[i].getNamespaceURI();
					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);
						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		*//**
		 * Register a namespace prefix
		 *//*
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);
			if (prefix == null) {
				prefix = generatePrefix(namespace);
				while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
			return prefix;
		}

		*//**
		 * databinding method to get an XML representation of this object
		 * 
		 *//*
		public javax.xml.stream.XMLStreamReader getPullParser(
				javax.xml.namespace.QName qName)
				throws org.apache.axis2.databinding.ADBException {

			java.util.ArrayList elementList = new java.util.ArrayList();
			java.util.ArrayList attribList = new java.util.ArrayList();

			if (localActivityIdTracker) {
				elementList
						.add(new javax.xml.namespace.QName(
								"http://messages.net.sms.wisdom.com/xsd",
								"activityId"));

				elementList.add(localActivityId == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localActivityId));
			}
			if (localEndDateTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "endDate"));

				elementList.add(localEndDate == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localEndDate));
			}
			if (localEndTimeTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "endTime"));

				elementList.add(localEndTime == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localEndTime));
			}
			if (localOrganizationIdTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd",
						"organizationId"));

				elementList.add(localOrganizationId == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localOrganizationId));
			}
			if (localRelateBusinessInfoTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd",
						"relateBusinessInfo"));

				elementList.add(localRelateBusinessInfo == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localRelateBusinessInfo));
			}
			if (localStartDateTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "startDate"));

				elementList.add(localStartDate == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localStartDate));
			}
			if (localStartTimeTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "startTime"));

				elementList.add(localStartTime == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localStartTime));
			}
			if (localTXInsuranceRequestTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd",
						"tXInsuranceRequest"));

				elementList.add(localTXInsuranceRequest == null ? null
						: localTXInsuranceRequest);
			}
			if (localTXInsuranceRequestExtensionTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd",
						"tXInsuranceRequestExtension"));

				elementList.add(localTXInsuranceRequestExtension == null ? null
						: localTXInsuranceRequestExtension);
			}
			if (localTaskIdTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "taskId"));

				elementList.add(localTaskId == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localTaskId));
			}
			if (localTaskTargetsTracker) {
				if (localTaskTargets != null) {
					for (int i = 0; i < localTaskTargets.length; i++) {

						if (localTaskTargets[i] != null) {
							elementList.add(new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"taskTargets"));
							elementList.add(localTaskTargets[i]);
						} else {

							elementList.add(new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"taskTargets"));
							elementList.add(null);

						}

					}
				} else {

					elementList.add(new javax.xml.namespace.QName(
							"http://messages.net.sms.wisdom.com/xsd",
							"taskTargets"));
					elementList.add(localTaskTargets);

				}

			}
			if (localTaskValueTracker) {
				elementList.add(new javax.xml.namespace.QName(
						"http://messages.net.sms.wisdom.com/xsd", "taskValue"));

				elementList.add(localTaskValue == null ? null
						: org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localTaskValue));
			}

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(
					qName, elementList.toArray(), attribList.toArray());

		}

		*//**
		 * Factory class that keeps the parse method
		 *//*
		public static class Factory {

			*//**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 *//*
			public static SpreadTask parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				SpreadTask object = new SpreadTask();

				int event;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";
				try {

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");
						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;
							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}
							nsPrefix = nsPrefix == null ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"SpreadTask".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);
								return (SpreadTask) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					java.util.ArrayList list11 = new java.util.ArrayList();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"activityId").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setActivityId(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"endDate").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setEndDate(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"endTime").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setEndTime(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"organizationId").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setOrganizationId(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"relateBusinessInfo").equals(reader
									.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setRelateBusinessInfo(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"startDate").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setStartDate(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"startTime").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setStartTime(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"tXInsuranceRequest").equals(reader
									.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if ("true".equals(nillableValue)
								|| "1".equals(nillableValue)) {
							object.setTXInsuranceRequest(null);
							reader.next();

							reader.next();

						} else {

							object.setTXInsuranceRequest(TXInsuranceRequest.Factory
									.parse(reader));

							reader.next();
						}
					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"tXInsuranceRequestExtension")
									.equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if ("true".equals(nillableValue)
								|| "1".equals(nillableValue)) {
							object.setTXInsuranceRequestExtension(null);
							reader.next();

							reader.next();

						} else {

							object.setTXInsuranceRequestExtension(TXInsuranceRequestExtension.Factory
									.parse(reader));

							reader.next();
						}
					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"taskId").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setTaskId(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"taskTargets").equals(reader.getName())) {

						// Process the array and step past its final element's
						// end.

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if ("true".equals(nillableValue)
								|| "1".equals(nillableValue)) {
							list11.add(null);
							reader.next();
						} else {
							list11.add(TaskTarget.Factory.parse(reader));
						}
						// loop until we find a start element that is not part
						// of this array
						boolean loopDone11 = false;
						while (!loopDone11) {
							// We should be at the end element, but make sure
							while (!reader.isEndElement())
								reader.next();
							// Step out of this element
							reader.next();
							// Step to next element event.
							while (!reader.isStartElement()
									&& !reader.isEndElement())
								reader.next();
							if (reader.isEndElement()) {
								// two continuous end elements means we are
								// exiting the xml structure
								loopDone11 = true;
							} else {
								if (new javax.xml.namespace.QName(
										"http://messages.net.sms.wisdom.com/xsd",
										"taskTargets").equals(reader.getName())) {

									nillableValue = reader
											.getAttributeValue(
													"http://www.w3.org/2001/XMLSchema-instance",
													"nil");
									if ("true".equals(nillableValue)
											|| "1".equals(nillableValue)) {
										list11.add(null);
										reader.next();
									} else {
										list11.add(TaskTarget.Factory
												.parse(reader));
									}
								} else {
									loopDone11 = true;
								}
							}
						}
						// call the converter utility to convert and set the
						// array

						object.setTaskTargets((TaskTarget[]) org.apache.axis2.databinding.utils.ConverterUtil
								.convertToArray(TaskTarget.class, list11));

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName(
									"http://messages.net.sms.wisdom.com/xsd",
									"taskValue").equals(reader.getName())) {

						nillableValue = reader.getAttributeValue(
								"http://www.w3.org/2001/XMLSchema-instance",
								"nil");
						if (!"true".equals(nillableValue)
								&& !"1".equals(nillableValue)) {

							java.lang.String content = reader.getElementText();

							object.setTaskValue(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(content));

						} else {

							reader.getElementText(); // throw away text nodes if
														// any.
						}

						reader.next();

					} // End of if for expected property start element

					else {

					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());

				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	private org.apache.axiom.om.OMElement toOM(SmsServiceStub.SendSMS param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(SmsServiceStub.SendSMS.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			SmsServiceStub.SendSMSResponse param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(SmsServiceStub.SendSMSResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			SmsServiceStub.CreateActivitySpreadTask param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					SmsServiceStub.CreateActivitySpreadTask.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			SmsServiceStub.CreateActivitySpreadTaskResponse param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					SmsServiceStub.CreateActivitySpreadTaskResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			SmsServiceStub.SendSMS param, boolean optimizeContent,
			javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope.getBody()
					.addChild(
							param.getOMElement(SmsServiceStub.SendSMS.MY_QNAME,
									factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	 methods to provide back word compatibility 

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			SmsServiceStub.CreateActivitySpreadTask param,
			boolean optimizeContent, javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault {

		try {

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							SmsServiceStub.CreateActivitySpreadTask.MY_QNAME,
							factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	 methods to provide back word compatibility 

	*//**
	 * get the default envelope
	 *//*
	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory) {
		return factory.getDefaultEnvelope();
	}

	private java.lang.Object fromOM(org.apache.axiom.om.OMElement param,
			java.lang.Class type, java.util.Map extraNamespaces)
			throws org.apache.axis2.AxisFault {

		try {

			if (SmsServiceStub.SendSMS.class.equals(type)) {

				return SmsServiceStub.SendSMS.Factory.parse(param
						.getXMLStreamReaderWithoutCaching());

			}

			if (SmsServiceStub.SendSMSResponse.class.equals(type)) {

				return SmsServiceStub.SendSMSResponse.Factory.parse(param
						.getXMLStreamReaderWithoutCaching());

			}

			if (SmsServiceStub.CreateActivitySpreadTask.class.equals(type)) {

				return SmsServiceStub.CreateActivitySpreadTask.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if (SmsServiceStub.CreateActivitySpreadTaskResponse.class
					.equals(type)) {

				return SmsServiceStub.CreateActivitySpreadTaskResponse.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

		} catch (java.lang.Exception e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
		return null;
	}

*/}
