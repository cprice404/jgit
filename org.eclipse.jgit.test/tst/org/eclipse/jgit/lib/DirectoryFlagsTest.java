package org.eclipse.jgit.lib;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.PrintWriter;

import static org.junit.Assert.assertEquals;

public class DirectoryFlagsTest extends LocalDiskRepositoryTestCase {
    @Test
    public void testAddFileWithNoGitLinks() throws Exception {
//////		File gitDir = createUniqueTestGitDir(false);
//////		File gitDir = createTempFile();
//////		FileUtils.mkdir(new File("./target/foo"));
//////		File gitDir = new File("./target/foo/nested");
//////		gitDir = createTestGitDir(gitDir, false);
//////
//////
//////		createWorkRepository(gitDir);
////
//////		FileUtils.mkdir(new File("./target/foo"));
//////		File nestedRepo = new File("./target/foo", "nested");
//////		createWorkRepository(nestedRepo);

//        FileRepository db = createWorkRepository();

        DirectoryFlags flags = new DirectoryFlags();
        flags.setNoGitLinks(true);

        File gitdir = createUniqueTestGitDir(false);
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        builder.setGitDir(gitdir);
        builder.setDirectoryFlags(flags);
        Repository db = builder.build();
        db.create();
//        toClose.add(db);


        File nestedRepoPath = new File(db.getWorkTree(), "sub/nested");

        FileRepositoryBuilder nestedBuilder = new FileRepositoryBuilder();
        nestedBuilder.setWorkTree(nestedRepoPath);
        nestedBuilder.build().create();

//        String repo = createWorkRepository();

//        FileUtils.mkdir(new File(db.getWorkTree(), "sub"));
//        File nestedRepo = new File(db.getWorkTree(), "sub/nested");
//        createWorkRepository(nestedRepo);

        File file = new File(nestedRepoPath, "a.txt");
        FileUtils.createNewFile(file);
        PrintWriter writer = new PrintWriter(file);
        writer.print("content");
        writer.close();

        System.out.println("Calling add command");
        Git git = new Git(db);
        git.add().addFilepattern("sub/nested/a.txt").call();
        System.out.println("Back from calling add command");

//        indexState(db, CONTENT);

        assertEquals(
                "[sub/nested/a.txt, mode:100644, content:content]",
                indexState(db, CONTENT));
    }

    // TODO: add a test for calling status after (and before?) doing
    //  an add on a nested repo
}
