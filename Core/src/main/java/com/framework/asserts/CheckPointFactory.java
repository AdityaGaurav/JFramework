package com.framework.asserts;

import com.framework.driver.event.HtmlElement;


/**
 * The interface provide checkpoint utilities methods to client project.
 */
public interface CheckPointFactory
{

	/**
	 * Creates a new checkpoint and returns a new <b>CheckpointAssert</b> instance.
	 *
	 * @param id  the checkpoint id.
	 *
	 * @return  a new {@link CheckpointAssert} instance.
	 *
	 * @see com.framework.asserts.CheckpointAssert
	 */
	CheckpointAssert createCheckPoint( final String id );

	/**
	 * Creates a new checkpoint with an {@code HtmlElement} as container for screenshot failure.
	 *
	 * @param element   the element container
	 * @param id        the checkpoint id.
	 *
	 * @return          a new {@link CheckpointAssert} instance.
	 *
	 * @see com.framework.asserts.CheckpointAssert
	 * @see com.framework.driver.event.HtmlElement
	 */
	CheckpointAssert createCheckPoint( HtmlElement element, final String id );

	/**
	 * Asserts all checkpoints accumulated in the test case
	 */
	void assertAllCheckpoints();
}
