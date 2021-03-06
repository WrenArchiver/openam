/*
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance with the
 * License.
 *
 * You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
 * specific language governing permission and limitations under the License.
 *
 * When distributing Covered Software, include this CDDL Header Notice in each file and include
 * the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
 * Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions copyright [year] [name of copyright owner]".
 *
 * Copyright 2014-2016 ForgeRock AS.
 */
package org.forgerock.openam.sm.datalayer.impl.tasks;

import java.util.Collection;
import org.forgerock.openam.cts.api.filter.TokenFilter;
import org.forgerock.openam.cts.api.tokens.Token;
import org.forgerock.openam.cts.continuous.ContinuousQueryListener;
import org.forgerock.openam.sm.datalayer.api.ResultHandler;
import org.forgerock.openam.sm.datalayer.api.Task;
import org.forgerock.openam.sm.datalayer.api.query.PartialToken;
import org.forgerock.util.Options;

/**
 * Generates instances of specific Task implementations.
 *
 * Tasks are processed asynchronously by the TaskProcessor.
 *
 * @see org.forgerock.openam.sm.datalayer.api.Task
 * @see org.forgerock.openam.sm.datalayer.impl.SeriesTaskExecutorThread
 */
public class TaskFactory {

    /**
     * Used to signal the creation of the given Token.
     *
     * @param token Non null. The token to create.
     * @param options Non null. The Options for the operation.
     * @return Non null. Token creation Task.
     */
    public Task create(Token token, Options options, ResultHandler<Token, ?> handler) {
        return new CreateTask(token, options, handler);
    }

    /**
     * Used to signal a read operation for the Token ID.
     *
     * @param tokenId Non null. The token ID to read.
     * @param options Non null. The Options for the operation.
     * @param handler Required handler to notify when operation is complete.
     * @return Non null. Token read Task.
     */
    public Task read(String tokenId, Options options, ResultHandler<Token, ?> handler) {
        return new ReadTask(tokenId, options, handler);
    }

    /**
     * Used to signal an update operation for the given Token.
     *
     * @param token Non null. The token ID to update.
     * @param options Non null. The Options for the operation.
     * @return Non null. Token update Task.
     */
    public Task update(Token token, Options options, ResultHandler<Token, ?> handler) {
        return new UpdateTask(token, options, handler);
    }

    /**
     * Used to signal a delete operation for the given Token ID.
     *
     * @param tokenId Non null. The token ID to delete.
     * @param options Non null. The Options for the operation.
     * @return Non null. Token delete Task.
     */
    public Task delete(String tokenId, Options options, ResultHandler<PartialToken, ?> handler) {
        return new DeleteTask(tokenId, options, handler);
    }

    /**
     * Used to signal a query against the persistence store.
     *
     * @param filter Non null. TokenFilter to use.
     * @param handler Non null. ResultHandler to be notified of the results.
     * @return Non null Token query Task.
     */
    public Task query(TokenFilter filter, ResultHandler<Collection<Token>, ?> handler) {
        return new QueryTask(filter, handler);
    }

    /**
     * Used to signal an attribute based query against the persistence store.
     *
     * @param filter Non null. TokenFilter to use.
     * @param handler Non null. ResultHandler to be notified of the results.
     * @return Non null Token query Task.
     */
    public Task partialQuery(TokenFilter filter, ResultHandler<Collection<PartialToken>, ?> handler) {
        return new PartialQueryTask(filter, handler);
    }

    /**
     * Used to signal an attribute based continuous query against the persistence store.
     *
     * @param filter Non null. TokenFilter to use.
     * @param listener Non null. ContinuousQueryListener to be updated by the resulting stream.
     * @return Non null. Token query Task.
     */
    public ContinuousQueryTask continuousQuery(TokenFilter filter, ContinuousQueryListener listener) {
        return new ContinuousQueryTask(filter, listener);
    }
}
