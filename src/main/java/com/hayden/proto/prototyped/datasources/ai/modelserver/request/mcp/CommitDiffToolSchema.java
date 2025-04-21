package com.hayden.proto.prototyped.datasources.ai.modelserver.request.mcp;

import com.fasterxml.jackson.annotation.*;
import com.hayden.proto.prototype.datasource.data.mcp_schema.DefaultValue;
import com.hayden.proto.prototype.datasource.data.mcp_schema.Description;
import com.hayden.proto.prototype.datasource.data.mcp_schema.Name;
import com.hayden.proto.prototype.datasource.data.mcp_schema.Required;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.Builder;

import java.util.UUID;

public sealed interface CommitDiffToolSchema extends ModelContextProtocolContextRequest.MpcToolsetRequest {

    sealed interface CommitDiffMcpParams extends ModelContextProtocolSchema.MpcParameters {

        /**
         * Each ToolCall, each function to be called on the server, has it's own context passed, which is like the request body.
         */



        @JsonTypeInfo(
                use = JsonTypeInfo.Id.NAME,
                include = JsonTypeInfo.As.EXISTING_PROPERTY,
                property = "name"
        )
        @JsonSubTypes({
                @JsonSubTypes.Type(value = CommitDiffCallToolParams.AddRepoParams.class, name = "add-repo")
        })
        sealed interface CommitDiffCallToolParams extends CommitDiffMcpParams {

            String name();


            @Name("null-param")
            record NullParam() implements CommitDiffCallToolParams, ModelContextProtocolSchema.MpcParameters.NullParam {
                @Override
                @JsonInclude(JsonInclude.Include.NON_EMPTY)
                public String name() {
                    return null;
                }
            }

            @Description("add repository to the database including adding commit diffs, but not including embedding the commit diffs.")
            @Name("add-repo")
            record AddRepoParams(CommitDiffParamArguments.AddRepoArgs arguments) implements CommitDiffCallToolParams {
                @Override
                public String name() {
                    return "add-repo";
                }
            }

            @Description("set embeddings for diffs in the repository in the database.")
            @Name("set-embeddings")
            record SetEmbeddingsParams(CommitDiffParamArguments.SetEmbeddingArgs arguments) implements CommitDiffCallToolParams {
                @Override
                public String name() {
                    return "set-embeddings";
                }
            }

            @Description("parse the history of the commits into clusters that are based on closeness to particular paths through the commit history.")
            @Name("parse-blame-tree")
            record ParseBlameTreeParams(CommitDiffParamArguments.ParseBlameTreeArgs arguments) implements CommitDiffCallToolParams {
                @Override
                public String name() {
                    return "parse-blame-tree";
                }
            }

//            @Description("for commit diffs that are in the repository, parse the abstract syntax tree around that code to add further context.")
//            @Name("add-ast")
//            record AddAstParams(CommitDiffParamArguments.AddAstArgs arguments) implements CommitDiffCallToolParams {
//                @Override
//                public String name() {
//                    return "add-ast";
//                }
//            }

            @Description("remove repository for the database, including any diffs and embeddings.")
            @Name("remove-repo")
            record RemoveRepoParams(CommitDiffParamArguments.RemoveRepoArgs arguments) implements CommitDiffCallToolParams {
                @Override
                public String name() {
                    return "remove-repo";
                }
            }

            @Description("add repository branch for the database, including parsing into commit diffs, but not including setting the embeddings for those commit diffs.")
            @Name("add-code-branch")
            record AddCodeBranchParams(CommitDiffParamArguments.AddCodeBranchArgs arguments) implements CommitDiffCallToolParams {
                @Override
                public String name() {
                    return "add-code-branch";
                }
            }

            @Description("remove repository branch for the database, including any embeddings or diffs for that branch.")
            @Name("remove-code-branch")
            record RemoveCodeBranchParams(CommitDiffParamArguments.RemoveCodeBranchArgs arguments) implements CommitDiffCallToolParams {
                @Override
                public String name() {
                    return "remove-code-branch";
                }
            }

//            @Description("add commit to the database after committing; intended for use as commit git hook.")
//            @Name("commit-update-head")
//            record CommitUpdateHeadParams(CommitDiffParamArguments.CommitUpdateHeadArgs arguments) implements CommitDiffCallToolParams {
//
//                @Override
//                public String name() {
//                    return "commit-update-head";
//                }
//            }

//            @Description("reset head for a branch, performing semantically a git reset according to the arguments.")
//            @Name("reset-head")
//            record ResetHeadParams(CommitDiffParamArguments.ResetHeadArgs arguments) implements CommitDiffCallToolParams {
//                @Override
//                public String name() {
//                    return "reset-head";
//                }
//            }

//            @Description("cherrypick commits into a branch, updating commits into the database for that branch.")
//            @Name("cherry-pick")
//            record CherryPickParams(CommitDiffParamArguments.CherryPickArgs arguments) implements CommitDiffCallToolParams {
//                @Override
//                public String name() {
//                    return "cherry-pick";
//                }
//            }

//            @Description("merge two branches together, updating commits in the database for that branch being merged into.")
//            @Name("merge")
//            record MergeParams(CommitDiffParamArguments.MergeArgs arguments) implements CommitDiffCallToolParams {
//                @Override
//                public String name() {
//                    return "merge";
//                }
//            }

//            @Description("performing git revert, reverting commits in the database for that branch.")
//            @Name("revert")
//            record RevertParams(CommitDiffParamArguments.RevertArgs arguments) implements CommitDiffCallToolParams {
//                @Override
//                public String name() {
//                    return "revert";
//                }
//            }

            @Description("get embeddings matching the arguments from the database.")
            @Name("get-embedding")
            record GetEmbeddingParams(CommitDiffParamArguments.GetEmbeddingArgs arguments) implements CommitDiffCallToolParams {
                @Override
                public String name() {
                    return "get-embedding";
                }
            }

        }

        sealed interface CommitDiffParamArguments
                extends ModelContextProtocolSchema.MpcParameter.McpParamArguments {

            sealed interface CommitDiffMcpArg extends McpArgumentParamArg {

                @Description("Url or path of the git repository")
                record RepoUrlArg(@JsonProperty("repoUrl") @JsonValue String repoUrl) implements CommitDiffMcpArg {
                }

                @Description("Branch name for the repository")
                record RepoBranchArg(@JsonProperty("branch") @JsonValue String branch) implements CommitDiffMcpArg {
                }

                @Description("Options for when setting embeddings in the database for the git request")
                record SetEmbeddingOptions(
                        @JsonProperty("options") @JsonValue DoParseGitOptions options) implements CommitDiffMcpArg {}

                @Description("Options to use when parsing the commit history using the blame tree method, which takes the top commits from a file closest to the query and parses them backwards recursively using ranking and similarity to other commit diffs, at which time clusters of commit diffs are created matching the query.")
                record DoBlameTreeOptions(
                        Integer maxBlameTreeDepth,
                        Boolean squashEmbed,
                        Integer maxTimeBlameTree,
                        Integer maxCommitDiffs,
                        Integer maxCommitsPerPath,
                        Integer maxDepthSingleBlameTree,
                        Integer topKCommitsPerSingleBlameTree,
                        Integer maxPathsParsedCommitDiffCluster,
                        Integer maxPathsConsideredGreedyDistinct,
                        Integer maxCommitDiffClustersForBranch) {}

                @Description("Options to use when parsing the repository for activities such as adding/removing branches or adding/removing git data to the database.")
                record DoParseGitOptions(
                        @Description("Maximum number of commits in the history to parse before stopping")
                        Integer maxCommitDepth,
                        @Description("Maximum number of diffs to add to the database before stopping")
                        Integer maxNumberDiffs
                ) {}


                @Description("Options to use when adding the code branch to the database.")
                record AddCodeBranchOptions(@JsonValue DoParseGitOptions gitOptions) implements CommitDiffMcpArg { }

                @Description("Options to use when adding the repository to the database.")
                record AddRepoOptions(@JsonValue DoParseGitOptions gitOptions) implements CommitDiffMcpArg {
                }

                @Description("Options to use when removing the repository to the database.")
                record RemoveRepoOptions(@JsonValue DoParseGitOptions gitOptions) implements CommitDiffMcpArg {
                }

                record ParseBlameTreeOptions(@JsonValue DoBlameTreeOptions blameTreeOptions)
                        implements CommitDiffMcpArg {}

//                record DoRagOptions(
//                        DoBlameTreeOptions blameTreeOptions,
//                        DoParseGitOptions parseGitOptions) {}

//                record RemoveCodeBranchOptions() implements CommitDiffMcpArg {
//                }

//                @Description("Options to use when committing to the repository.")
//                record CommitUpdateHeadOptions() implements CommitDiffMcpArg {
//                }

//                @Description("Options to use when resetting a branch to a previous commit.")
//                record ResetHeadOptions(String commitHashToResetTo,
//                                        ResetType resetType) implements CommitDiffMcpArg {
//
//                    public enum ResetType {
//                        HARD, SOFT
//                    }
//
//                }


//                record CherryPickOptions(String[] commitHashesToCherryPick) implements CommitDiffMcpArg {
//                }

//                record RevertOptions(String[] commitHashesToRevert) implements CommitDiffMcpArg {
//                }


//                record RebaseOptions() implements CommitDiffMcpArg {
//                }

//                record MergeOptions() implements CommitDiffMcpArg {
//                }

//                record AddAstOptions() implements CommitDiffMcpArg {
//                }

            }

            record SetEmbeddingArgs(CommitDiffMcpArg.RepoUrlArg repoUrl,
                                    CommitDiffMcpArg.RepoBranchArg branch,
                                    CommitDiffMcpArg.SetEmbeddingOptions options) implements CommitDiffParamArguments {
            }

            record ParseBlameTreeArgs(CommitDiffMcpArg.RepoUrlArg repoUrl,
                                        CommitDiffMcpArg.RepoBranchArg branch,
                                        CommitDiffMcpArg.ParseBlameTreeOptions options) implements CommitDiffParamArguments {
            }

//            record AddAstArgs(CommitDiffMcpArg.RepoUrlArg repoUrl,
//                                CommitDiffMcpArg.RepoBranchArg branch,
//                                CommitDiffMcpArg.AddAstOptions options) implements CommitDiffParamArguments {
//            }

            record AddRepoArgs(CommitDiffMcpArg.RepoUrlArg repoUrl,
                                 CommitDiffMcpArg.RepoBranchArg branch,
                                 CommitDiffMcpArg.AddRepoOptions options) implements CommitDiffParamArguments {
            }

            record RemoveRepoArgs(CommitDiffMcpArg.RepoUrlArg repoUrl,
                                    CommitDiffMcpArg.RepoBranchArg branch,
                                    CommitDiffMcpArg.RemoveRepoOptions options) implements CommitDiffParamArguments {
            }

            record AddCodeBranchArgs(CommitDiffMcpArg.RepoUrlArg repoUrl,
                                       CommitDiffMcpArg.RepoBranchArg branch,
                                       CommitDiffMcpArg.AddCodeBranchOptions options) implements CommitDiffParamArguments {
            }

            record RemoveCodeBranchArgs(CommitDiffMcpArg.RepoUrlArg repoUrl,
                                          CommitDiffMcpArg.RepoBranchArg branch) implements CommitDiffParamArguments {
            }

            // TODO; generate next commit
            @Description("Generate next commit for repository")
            record NextCommitArgs(CommitDiffMcpArg.RepoUrlArg repoUrl,
                                  CommitDiffMcpArg.RepoBranchArg branch) implements CommitDiffParamArguments {
            }

            /// Interact with commit diff context
            @Description("Browse commit diff context history for previous commits and contexts")
            record BrowseCommitDiffContextHistory(CommitDiffMcpArg.RepoUrlArg repoUrl,
                                                  CommitDiffMcpArg.RepoBranchArg branch,
                                                  String sessionKey) implements CommitDiffParamArguments {
            }

            @Description("Delete commit diff context chat traces from the commit diff context history")
            record DeleteFromCommitDiffContextHistory(CommitDiffMcpArg.RepoUrlArg repoUrl,
                                                      CommitDiffMcpArg.RepoBranchArg branch,
                                                      Long[] chatTraceIdsToDelete,
                                                      String sessionKey) implements CommitDiffParamArguments {
            }

            @Description("Delete entire commit diff context chat history to restart from beginning")
            record ClearCommitDiffContextChatContextData(CommitDiffMcpArg.RepoUrlArg repoUrl,
                                                         CommitDiffMcpArg.RepoBranchArg branch,
                                                         Long commitDiffContextToDelete,
                                                         String sessionKey) implements CommitDiffParamArguments {
            }

            @Description("Delete entire commit diff context session to restart everything from beginning")
            record ClearCommitDiffContextSessionEntirely(String sessionKeyForSessionToDelete) implements CommitDiffParamArguments {
            }

            @Description("Delete commit diff context data from a commit diff context chat trace")
            record DeleteContextDataFromCommitDiffContextChatTrace(CommitDiffMcpArg.RepoUrlArg repoUrl,
                                                                   CommitDiffMcpArg.RepoBranchArg branch,
                                                                   Long[] commitDiffContextDataItemsToDelete,
                                                                   String sessionKey) implements CommitDiffParamArguments {
            }

//            record CommitUpdateHeadArgs(CommitDiffMcpArg.RepoUrlArg repoUrl,
//                                          CommitDiffMcpArg.RepoBranchArg branch,
//                                          CommitDiffMcpArg.CommitUpdateHeadOptions options) implements CommitDiffParamArguments {
//
//            }

//            record ResetHeadArgs(CommitDiffMcpArg.RepoUrlArg repoUrl,
//                                   CommitDiffMcpArg.RepoBranchArg branch,
//                                   CommitDiffMcpArg.ResetHeadOptions options) implements CommitDiffParamArguments {
//            }

//            record CherryPickArgs(CommitDiffMcpArg.RepoUrlArg repoUrl,
//                                    CommitDiffMcpArg.RepoBranchArg branch,
//                                    CommitDiffMcpArg.CherryPickOptions options) implements CommitDiffParamArguments {
//            }


//            record MergeArgs(CommitDiffMcpArg.RepoUrlArg repoUrl,
//                               CommitDiffMcpArg.RepoBranchArg branch,
//                               CommitDiffMcpArg.MergeOptions options) implements CommitDiffParamArguments {
//            }

//            record RevertArgs(CommitDiffMcpArg.RepoUrlArg repoUrl,
//                              CommitDiffMcpArg.RepoBranchArg branch,
//                              CommitDiffMcpArg.RevertOptions options) implements CommitDiffParamArguments {
//            }

//            record RebaseArgs(CommitDiffMcpArg.RepoUrlArg repoUrl,
//                              CommitDiffMcpArg.RepoBranchArg branch,
//                              CommitDiffMcpArg.RebaseOptions options) implements CommitDiffParamArguments {
//            }

            record GetEmbeddingArgs(CommitDiffMcpArg.RepoUrlArg repoUrl,
                                    CommitDiffMcpArg.RepoBranchArg branch) implements CommitDiffParamArguments {
            }


        }

        record CommitDiffListToolsArguments() implements CommitDiffMcpParams, NullParam {

            @Override
            public String name() {
                return null;
            }
        }

    }

    @Builder
    record CommitDiffToolsetRequestSchema(@Required @DefaultValue("2.0")
                                          @JsonProperty("jsonrpc")
                                          String jsonRpc,
                                          @Required String method,
                                          @Required String id,
                                          @JsonInclude(JsonInclude.Include.NON_EMPTY)
                                          CommitDiffMcpParams.CommitDiffCallToolParams params) implements CommitDiffToolSchema {

        public CommitDiffToolsetRequestSchema(CommitDiffMcpParams.CommitDiffCallToolParams params) {
            this("2.0", "results/call", UUID.randomUUID().toString(),
                    params instanceof ModelContextProtocolSchema.MpcParameters.NullParam ? null : params);
        }

        @JsonValue
        public ModelContextProtocolContextRequest.ToolsetRequest callToolRequest() {
//            return new McpSchema.JSONRPCRequest(jsonRpc, method, id, params);
            return null;
        }
    }

    @Builder
    record CommitDiffListToolsSchema(@Required @DefaultValue("2.0") @JsonProperty("jsonrpc")
                                     String jsonRpc,
                                     @Required String method,
                                     @Required String id,
                                     @JsonInclude(JsonInclude.Include.NON_EMPTY)
                                     CommitDiffMcpParams params) implements CommitDiffToolSchema {

        public CommitDiffListToolsSchema(CommitDiffMcpParams params) {
            this("2.0", "results/list", UUID.randomUUID().toString(),
                    params instanceof ModelContextProtocolSchema.MpcParameters.NullParam ? null : params);
        }

        @JsonValue
        public ModelContextProtocolContextRequest.ToolsetRequest callToolRequest() {
//            return new McpSchema.JSONRPCRequest(jsonRpc, method, id, params);
            return null;
        }
    }

    @Builder
    record CommitDiffReadResourcesSchema(@Required @DefaultValue("2.0") @JsonProperty("jsonrpc")
                                         String jsonRpc,
                                         @Required
                                         String method,
                                         @Required
                                         String id,
                                         @JsonInclude(JsonInclude.Include.NON_EMPTY)
                                         CommitDiffMcpParams params) implements CommitDiffToolSchema {

        public CommitDiffReadResourcesSchema(CommitDiffMcpParams params) {
            this("2.0", "resources/read", UUID.randomUUID().toString(),
                    params instanceof ModelContextProtocolSchema.MpcParameters.NullParam ? null : params);
        }

        @JsonValue
        public ModelContextProtocolContextRequest.ToolsetRequest callToolRequest() {
//            return new McpSchema.JSONRPCRequest(jsonRpc, method, id, params);
            return null;
        }
    }

    @Builder
    record CommitDiffListResourcesSchema(@Required @DefaultValue("2.0") @JsonProperty("jsonrpc")
                                         String jsonRpc,
                                         @Required
                                         String method,
                                         @Required
                                         String id,
                                         @JsonInclude(JsonInclude.Include.NON_EMPTY)
                                         CommitDiffMcpParams params) implements CommitDiffToolSchema {

        public CommitDiffListResourcesSchema(CommitDiffMcpParams params) {
            this("2.0", "resources/list", UUID.randomUUID().toString(),
                    params instanceof ModelContextProtocolSchema.MpcParameters.NullParam ? null : params);
        }

        @JsonIgnore
        @JsonValue
        public ModelContextProtocolContextRequest.ToolsetRequest callToolRequest() {
//            return new McpSchema.JSONRPCRequest(jsonRpc, method, id, params);
            return null;
        }
    }

}
