package com.hayden.proto.prototyped.datasources.ai.modelserver.request.mcp;

public interface ModelContextProtocolSchema {

    sealed interface MpcParameters
            permits CommitDiffToolSchema.CommitDiffMcpParams, PostgresToolSchema.PostgresMcpParams {

        interface NullParam {}

        String name();

    }

    sealed interface MpcParameter {

        sealed interface McpParamArguments extends MpcParameter
                permits CommitDiffToolSchema.CommitDiffMcpParams.CommitDiffParamArguments, PostgresToolSchema.PostgresParamArguments {

            sealed interface McpArgumentParamArg
                    permits
                        CommitDiffToolSchema.CommitDiffMcpParams.CommitDiffParamArguments.CommitDiffMcpArg,
                        PostgresToolSchema.PostgresMcpParams.PostgresMpcArg {
            }

        }
    }
}
