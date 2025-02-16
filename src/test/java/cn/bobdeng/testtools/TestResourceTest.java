package cn.bobdeng.testtools;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import org.junit.Test;

public class TestResourceTest {

  @Test
  public void should_read_json() throws IOException {
    TestResource testResource = new TestResource(this, "test_read.json");
    String content = testResource.readString();
    assertThat(content, is("{\r\n  \"name\": \"hello\"\r\n}\r\n"));
  }

  @Test
  public void should_get_json_when_write() throws Exception {
    TestResource testResource = new TestResource(this, "test_write_object.json");
    TestForm testForm = new TestForm("hello world!");
    testResource.saveObject(testForm);
    assertThat(testResource.readString(), is(new Gson().toJson(testForm)));
  }

  @Test
  public void should_get_file_when_write() throws Exception {
    TestResource testResource = new TestResource(this, "test_write_string.json");
    String content = "hello world!";
    testResource.save(content);
    assertThat(testResource.readString(), is(content));
  }

  @Test
  public void should_get_bytes_when_save_bytes() throws Exception {
    TestResource testResource = new TestResource(this, "test_write_bin.bin");
    testResource.save("123456".getBytes());
    assertThat(new String(testResource.readBytes()), is("123456"));
  }

  @Test
  public void test_file_exist() {
    assertThat(new TestResource(this, "test_read.json").exist(), is(true));
    assertThat(new TestResource(this, "not_exist.json").exist(), is(false));
  }

  @Test
  public void read_to_class() throws IOException {
    TestForm testForm = new TestResource(this, "test_read.json").read(TestForm.class);
    assertThat(testForm, is(new TestForm("hello")));
  }

  @Test
  public void Given有子目录_When写入测试文件_Then写到子目录去() throws IOException {
    TestResource testResource =
        new TestResource(this, File.separator + "abc.snapshot", "_snapshots_");
    testResource.saveObject(new TestForm("sub folder"));
  }
}
