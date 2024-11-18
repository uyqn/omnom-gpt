import {danger, fail, markdown, message} from "danger";

const prBody = danger.github.pr.body;
const linkedIssuePattern = /(- (Closes|Fixes|Resolves) #\d+\s*)+/;

if (!linkedIssuePattern.test(prBody)) {
    const issueNumber = danger.github.pr.head.ref.match(/\d+/)?.[0];
    fail(`Please link to an issue in the PR description, See example below! 
    
    For more information, see [linking a pull request to an issue](https://docs.github.com/en/issues/tracking-your-work-with-issues/using-issues/linking-a-pull-request-to-an-issue).`);
    markdown(`
    ### Linked issues
    
    - Closes #${issueNumber}
    `);
}